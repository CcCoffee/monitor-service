package com.study.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.monitor.modal.entity.MonitoringRuleChannelEntity;
import com.study.monitor.modal.entity.ServerMonitoringRuleEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MonitoringRuleChannelMapper extends BaseMapper<MonitoringRuleChannelEntity> {
}
