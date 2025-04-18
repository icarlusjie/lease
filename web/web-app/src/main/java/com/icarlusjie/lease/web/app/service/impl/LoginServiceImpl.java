package com.icarlusjie.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.icarlusjie.lease.common.constant.RedisConstant;
import com.icarlusjie.lease.common.exception.LeaseException;
import com.icarlusjie.lease.common.result.ResultCodeEnum;
import com.icarlusjie.lease.common.utils.JwtUtils;
import com.icarlusjie.lease.common.utils.VerifyCodeUtil;
import com.icarlusjie.lease.model.entity.UserInfo;
import com.icarlusjie.lease.model.enums.BaseStatus;
import com.icarlusjie.lease.web.app.mapper.UserInfoMapper;
import com.icarlusjie.lease.web.app.service.LoginService;
import com.icarlusjie.lease.web.app.service.SmsService;
import com.icarlusjie.lease.web.app.service.UserInfoService;
import com.icarlusjie.lease.web.app.vo.user.LoginVo;
import com.icarlusjie.lease.web.app.vo.user.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SmsService smsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public void getCode(String phone) {
        String verifyCode = VerifyCodeUtil.getVerifyCode(6);
        String key = RedisConstant.APP_LOGIN_PREFIX+phone;
        //不能让验证码发送过于频繁
        Boolean aBoolean = stringRedisTemplate.hasKey(key);
        if (aBoolean){
            Long ttl = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (RedisConstant.APP_LOGIN_CODE_TTL_SEC-ttl < RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC){
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }
        //发送短信
        smsService.sendCode(phone,verifyCode);
        //存到redis
        stringRedisTemplate.opsForValue().set(key,verifyCode,RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
    }

    @Override
    public String login(LoginVo loginVo) {
        //1.判断手机号码和验证码是否为空
        if (!StringUtils.hasText(loginVo.getPhone())){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }
        if (!StringUtils.hasText(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }
        //2.校验验证码
        String key = RedisConstant.APP_LOGIN_PREFIX+loginVo.getPhone();
        String code = stringRedisTemplate.opsForValue().get(key);
        if (!code.equals(loginVo.getCode())){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }
        //3.判断用户是否存在,不存在则注册（创建用户）
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, loginVo.getPhone());
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfo.setNickname("用户-"+loginVo.getPhone().substring(6));
            userInfoService.save(userInfo);
        }

        //4.判断用户是否被禁
        if (userInfo.getStatus().equals(BaseStatus.DISABLE)) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }
        //5.创建并返回TOKEN
        return JwtUtils.createToken(userInfo.getId(), loginVo.getPhone());
    }

    @Override
    public UserInfoVo getUserInfoById(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        UserInfoVo userInfoVo = new UserInfoVo(userInfo.getNickname(),userInfo.getAvatarUrl());

        return userInfoVo;
    }
}
