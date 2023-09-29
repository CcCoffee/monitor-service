package com.study.monitor.dto;

import com.study.monitor.domain.LogMonitoringRule;
import com.study.monitor.domain.ProcessMonitoringRule;
import com.study.monitor.domain.Server;

import java.util.ArrayList;
import java.util.List;

public class ServerRulesDTO {

    private Server server;
    private List<LogMonitoringRule> logMonitoringRuleList = new ArrayList<>();
    private List<ProcessMonitoringRule> processMonitoringRuleList = new ArrayList<>();

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

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
