package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.mapper.NotificationMapper;
import com.study.monitor.modal.entity.NotificationEntity;
import com.study.monitor.modal.request.NotificationQO;
import com.study.monitor.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, NotificationEntity> implements NotificationService {
    @Override
    public IPage<NotificationEntity> selectMyPage(Page<NotificationEntity> page, NotificationQO notificationQO) {
        return null;
    }
}
