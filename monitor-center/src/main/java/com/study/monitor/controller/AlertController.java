package com.study.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.modal.entity.AlertEntity;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.modal.request.AlertQO;
import com.study.monitor.modal.response.ApiResponse;
import com.study.monitor.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService){
        this.alertService = alertService;
    }

    @GetMapping({"", "/"})
    public ApiResponse<IPage<AlertEntity>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize,
                                                AlertQO alertQO) {
        Page<ServerEntity> page = new Page<>(pageNum, pageSize);
        return ApiResponse.success(alertService.selectMyPage(page, alertQO));
    }

    @PostMapping({"", "/"})
    public ApiResponse<Boolean> save(@RequestBody AlertEntity alertEntity) {
        return ApiResponse.success(alertService.saveOrUpdateEntity(alertEntity));
    }

    @RequestMapping("/applications")
    public ApiResponse<List<String>> getAllApplicationName(AlertQO alertQO){
        return ApiResponse.success(alertService.getAllApplicationName(alertQO));
    }

    @GetMapping("/{alertId}")
    public ApiResponse<AlertEntity> getById(@PathVariable(name = "alertId") Integer alertId){
        return ApiResponse.success(alertService.getById(alertId));
    }

    @PatchMapping("/{alertId}/status")
    public ApiResponse<Boolean> updateStatus(@PathVariable(name = "alertId") Integer alertId,
                                             @RequestParam(name = "status") String status){
        if (!status.equalsIgnoreCase("Acknowledged")  && !status.equalsIgnoreCase("Closed")) {
            return ApiResponse.badRequest("Invalid status");
        }
        return ApiResponse.success(alertService.updateStatus(alertId, status));
    }
}
