package com.study.monitor.dto;

import com.study.monitor.entity.LogMonitoringRuleEntity;
import com.study.monitor.entity.ProcessMonitoringRuleEntity;
import com.study.monitor.entity.ServerEntity;

import java.util.ArrayList;
import java.util.List;

public class ServerRulesDTO {

    private ServerEntity server;
    private List<LogMonitoringRuleEntity> logMonitoringRuleList = new ArrayList<>();
    private List<ProcessMonitoringRuleEntity> processMonitoringRuleList = new ArrayList<>();

    public ServerEntity getServer() {
        return server;
    }

    public void setServer(ServerEntity server) {
        this.server = server;
    }

    public List<LogMonitoringRuleEntity> getLogMonitoringRuleList() {
        return logMonitoringRuleList;
    }

    public void setLogMonitoringRuleList(List<LogMonitoringRuleEntity> logMonitoringRuleList) {
        this.logMonitoringRuleList = logMonitoringRuleList;
    }

    public List<ProcessMonitoringRuleEntity> getProcessMonitoringRuleList() {
        return processMonitoringRuleList;
    }

    public void setProcessMonitoringRuleList(List<ProcessMonitoringRuleEntity> processMonitoringRuleList) {
        this.processMonitoringRuleList = processMonitoringRuleList;
    }
}
