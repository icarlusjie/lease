package com.icarlusjie.lease.web.admin.schedule;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.icarlusjie.lease.model.entity.LeaseAgreement;
import com.icarlusjie.lease.model.enums.LeaseStatus;
import com.icarlusjie.lease.web.admin.service.LeaseAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName : ScheduledTasks
 * @Description : 定时任务器
 * @Author : 吴煌杰
 */
@Component
public class ScheduledTasks {
    @Autowired
    private LeaseAgreementService leaseAgreementService;
    @Scheduled(cron = "0 0 0 * * *")
    public void checkLeaseStatus() {
        LambdaUpdateWrapper<LeaseAgreement> leaseAgreementLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        leaseAgreementLambdaUpdateWrapper.le(LeaseAgreement::getLeaseEndDate,new Date());
        leaseAgreementLambdaUpdateWrapper.in(LeaseAgreement::getStatus, LeaseStatus.SIGNED, LeaseStatus.WITHDRAWING);
        leaseAgreementLambdaUpdateWrapper.set(LeaseAgreement::getStatus,LeaseStatus.EXPIRED);
        leaseAgreementService.update(leaseAgreementLambdaUpdateWrapper);
    }
}
