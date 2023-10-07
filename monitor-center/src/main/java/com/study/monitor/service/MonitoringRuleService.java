package com.study.monitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.monitor.dto.ServerRulesDTO;
import com.study.monitor.entity.MonitoringRuleEntity;

import java.util.List;

public interface MonitoringRuleService extends IService<MonitoringRuleEntity> {

    List<ServerRulesDTO> getAllRules();
}