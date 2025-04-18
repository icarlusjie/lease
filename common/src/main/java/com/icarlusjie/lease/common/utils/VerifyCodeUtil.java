package com.icarlusjie.lease.common.utils;

import java.util.Random;

/**
 * @ClassName : VerifyCodeUtil
 * @Description : 生成验证码
 * @Author : 吴煌杰
 */
public class VerifyCodeUtil {
    public static String getVerifyCode(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i<length; i++){
            int num = random.nextInt(10);
            builder.append(num);
        }
        return builder.toString();
    }
}
