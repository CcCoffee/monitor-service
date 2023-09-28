package com.study.monitor.dto;

import com.study.monitor.domain.LogMonitoringRule;
import com.study.monitor.domain.ProcessMonitoringRule;

import java.util.ArrayList;
import java.util.List;

public class MonitorRulesDTO {

    private List<LogMonitoringRule> logMonitoringRuleList;
    private List<ProcessMonitoringRule> processMonitoringRuleList;

    public List<LogMonitoringRule> getLogMonitoringRuleList() {
        return logMonitoringRuleList;
    }

    public void setLogMonitoringRuleList(List<LogMonitoringRule> logMonitoringRuleList) {
        this.logMonitoringRuleList = logMonitoringRuleList;
    }

    public List<ProcessMonitoringRule> getProcessMonitoringRuleList() {
        return processMonitoringRuleList;
    }

    public void setProcessMonitoringRuleList(List<ProcessMonitoringRule> processMonitoringRuleList) {
        this.processMonitoringRuleList = processMonitoringRuleList;
    }
}
