package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.modal.entity.ServerMonitoringRuleEntity;
import com.study.monitor.mapper.ServerMonitoringRuleMapper;
import com.study.monitor.service.ServerMonitoringRuleService;
import org.springframework.stereotype.Service;

@Service
public class ServerMonitoringRuleServiceImpl extends ServiceImpl<ServerMonitoringRuleMapper, ServerMonitoringRuleEntity> implements ServerMonitoringRuleService {
}
