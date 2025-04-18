package com.icarlusjie.lease.web.admin.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @ClassName : ScheduledTasksTest
 * @Description :
 * @Author : 吴煌杰
 */
@SpringBootTest
class ScheduledTasksTest {
    @Autowired
    private ScheduledTasks scheduledTasks;

    @Test
    public void test(){
        scheduledTasks.checkLeaseStatus();
    }
}