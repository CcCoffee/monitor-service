package com.study.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.monitor.modal.entity.AlertEntity;
import com.study.monitor.modal.entity.NotificationEntity;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.modal.request.AlertQO;
import com.study.monitor.modal.request.NotificationQO;

import java.util.List;

public interface NotificationService extends IService<NotificationEntity> {

    IPage<NotificationEntity> selectMyPage(Page<NotificationEntity> page, NotificationQO notificationQO);

    List<String> selectNotificationChannelTypes(Integer alertId);

//    Boolean saveOrUpdateEntity(NotificationEntity alertEntity);

}