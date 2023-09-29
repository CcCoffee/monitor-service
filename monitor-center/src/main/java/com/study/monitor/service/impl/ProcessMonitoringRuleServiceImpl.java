package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.entity.ProcessMonitoringRuleEntity;
import com.study.monitor.mapper.ProcessMonitoringRuleMapper;
import com.study.monitor.service.ProcessMonitoringRuleService;
import org.springframework.stereotype.Service;

@Service
public class ProcessMonitoringRuleServiceImpl extends ServiceImpl<ProcessMonitoringRuleMapper, ProcessMonitoringRuleEntity> implements ProcessMonitoringRuleService {
}