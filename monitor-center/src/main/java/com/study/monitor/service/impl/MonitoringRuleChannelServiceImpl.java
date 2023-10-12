package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.mapper.MonitoringRuleChannelMapper;
import com.study.monitor.modal.entity.MonitoringRuleChannelEntity;
import com.study.monitor.modal.entity.ServerMonitoringRuleEntity;
import com.study.monitor.service.MonitoringRuleChannelService;
import com.study.monitor.service.ServerMonitoringRuleService;
import org.springframework.stereotype.Service;

@Service
public class MonitoringRuleChannelServiceImpl extends ServiceImpl<MonitoringRuleChannelMapper, MonitoringRuleChannelEntity> implements MonitoringRuleChannelService {
}