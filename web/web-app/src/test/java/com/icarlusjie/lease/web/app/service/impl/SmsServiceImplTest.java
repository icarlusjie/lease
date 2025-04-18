package com.icarlusjie.lease.web.app.service.impl;

import com.icarlusjie.lease.web.app.service.SmsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName : SmsServiceImplTest
 * @Description :
 * @Author : 吴煌杰
 */
@SpringBootTest
class SmsServiceImplTest {
    @Autowired
    private SmsService service;
    @Test
    void sendCode() {
        service.sendCode("13645926357","1234");
    }
}