package com.study.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.monitor.modal.entity.NotificationEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotificationMapper extends BaseMapper<NotificationEntity> {
}