package com.study.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.modal.entity.NotificationEntity;
import com.study.monitor.modal.request.NotificationQO;
import com.study.monitor.modal.response.ApiResponse;
import com.study.monitor.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @GetMapping("/alerts/{alertId}/notifications")
    public ApiResponse<IPage<NotificationEntity>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                                       @PathVariable(name = "alertId") Integer alertId,
                                                       NotificationQO notificationQO) {
        Page<NotificationEntity> page = new Page<>(pageNum, pageSize);
        notificationQO.setAlertId(alertId);
        return ApiResponse.success(notificationService.selectMyPage(page, notificationQO));
    }

    @GetMapping("/alerts/{alertId}/notifications-channel-types")
    public ApiResponse<List<String>> selectNotificationChannelTypes(@PathVariable(name = "alertId") Integer alertId) {
        return ApiResponse.success(notificationService.selectNotificationChannelTypes(alertId));
    }
}
