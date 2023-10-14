package com.study.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.monitor.modal.dto.ServerRulesDTO;
import com.study.monitor.modal.entity.MonitoringRuleEntity;
import com.study.monitor.modal.entity.ServerMonitoringRuleEntity;
import com.study.monitor.modal.request.RuleQO;

import java.util.List;

public interface MonitoringRuleService extends IService<MonitoringRuleEntity> {

    List<ServerRulesDTO> getAllServerRules();

    MonitoringRuleEntity selectWithServersById(Integer ruleId);

    boolean saveServerMonitoringRule(ServerMonitoringRuleEntity serverMonitoringRuleEntity);

    boolean saveOrUpdateEntity(MonitoringRuleEntity rule);

    boolean deleteById(Integer ruleId);

    IPage<MonitoringRuleEntity> selectMyPage(Page<MonitoringRuleEntity> page, RuleQO ruleQO);

    List<String> getAllApplicationName(RuleQO ruleQO);

}