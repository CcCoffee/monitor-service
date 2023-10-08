package com.study.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.monitor.dto.ServerRulesDTO;
import com.study.monitor.entity.MonitoringRuleEntity;
import com.study.monitor.entity.ServerMonitoringRuleEntity;

import java.util.List;

public interface MonitoringRuleService extends IService<MonitoringRuleEntity> {

    List<ServerRulesDTO> getAllServerRules();

    IPage<MonitoringRuleEntity> selectPage(IPage<MonitoringRuleEntity> page);

    MonitoringRuleEntity selectWithServersById(Integer ruleId);

    boolean saveServerMonitoringRule(ServerMonitoringRuleEntity serverMonitoringRuleEntity);

    boolean saveOrUpdateEntity(MonitoringRuleEntity rule);

    boolean deleteById(Integer ruleId);
}