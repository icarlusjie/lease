package com.icarlusjie.lease.web.admin.controller.lease;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.icarlusjie.lease.common.result.Result;
import com.icarlusjie.lease.model.entity.ViewAppointment;
import com.icarlusjie.lease.model.enums.AppointmentStatus;
import com.icarlusjie.lease.web.admin.service.ViewAppointmentService;
import com.icarlusjie.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.icarlusjie.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "预约看房管理")
@RequestMapping("/admin/appointment")
@RestController
public class ViewAppointmentController {
    @Autowired
    private ViewAppointmentService viewAppointmentService;

    @Operation(summary = "分页查询预约信息")
    @GetMapping("page")
    public Result<IPage<AppointmentVo>> page(@RequestParam long current, @RequestParam long size, AppointmentQueryVo queryVo) {
        IPage<AppointmentVo> page = new Page<>(current,size);
        IPage<AppointmentVo> list = viewAppointmentService.pageAppointmentByQuery(queryVo, page);
        return Result.ok(list);
    }

    @Operation(summary = "根据id更新预约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam AppointmentStatus status) {
        LambdaUpdateWrapper<ViewAppointment> viewAppointmentLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        viewAppointmentLambdaUpdateWrapper.eq(ViewAppointment::getId,id);
        viewAppointmentLambdaUpdateWrapper.set(ViewAppointment::getAppointmentStatus,status);
        viewAppointmentService.update(viewAppointmentLambdaUpdateWrapper);
        return Result.ok();
    }

}
