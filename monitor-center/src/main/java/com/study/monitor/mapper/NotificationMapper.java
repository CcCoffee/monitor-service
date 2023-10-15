package com.study.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.modal.entity.NotificationEntity;
import com.study.monitor.modal.request.NotificationQO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NotificationMapper extends BaseMapper<NotificationEntity> {
    IPage<NotificationEntity> selectMyPage(Page<NotificationEntity> page, NotificationQO notificationQO);

    List<String> selectNotificationChannelTypes(Integer alertId);
}