package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.entity.LogMonitoringRuleEntity;
import com.study.monitor.mapper.LogMonitoringRuleMapper;
import com.study.monitor.service.LogMonitoringRuleService;
import org.springframework.stereotype.Service;

@Service
public class LogMonitoringRuleServiceImpl extends ServiceImpl<LogMonitoringRuleMapper, LogMonitoringRuleEntity> implements LogMonitoringRuleService {
}
