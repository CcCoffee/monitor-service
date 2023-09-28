package com.study.monitor.dto;


import com.study.monitor.entity.LogMonitoringRuleEntity;
import com.study.monitor.entity.ProcessMonitoringRuleEntity;

import java.util.ArrayList;
import java.util.List;

public class MonitorRulesDTO {

    private List<LogMonitoringRuleEntity> logMonitoringRuleEntityList = new ArrayList<>();
    private List<ProcessMonitoringRuleEntity> processMonitoringRuleEntityList = new ArrayList<>();

    public List<LogMonitoringRuleEntity> getLogMonitoringRuleList() {
        return logMonitoringRuleEntityList;
    }

    public void setLogMonitoringRuleList(List<LogMonitoringRuleEntity> logMonitoringRuleEntityList) {
        this.logMonitoringRuleEntityList = logMonitoringRuleEntityList;
    }

    public List<ProcessMonitoringRuleEntity> getProcessMonitoringRuleList() {
        return processMonitoringRuleEntityList;
    }

    public void setProcessMonitoringRuleList(List<ProcessMonitoringRuleEntity> processMonitoringRuleEntityList) {
        this.processMonitoringRuleEntityList = processMonitoringRuleEntityList;
    }
}
