package com.icarlusjie.lease.web.app.service;

import com.icarlusjie.lease.web.app.vo.user.LoginVo;
import com.icarlusjie.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {
    void getCode(String phone);

    String login(LoginVo loginVo);

    UserInfoVo getUserInfoById(Long userId);
}
