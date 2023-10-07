package com.study.monitor.dto;

import com.study.monitor.domain.MonitoringRule;
import com.study.monitor.domain.Server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerRulesDTO {

    private Integer id;
    private String serverName;
    private String hostname;
    private String description;
    private String createdBy;
    private String updatedBy;
    private Date createDate;
    private Date updateDate;
    private List<MonitoringRule> monitoringRuleList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<MonitoringRule> getMonitoringRuleList() {
        return monitoringRuleList;
    }

    public void setMonitoringRuleList(List<MonitoringRule> monitoringRuleList) {
        this.monitoringRuleList = monitoringRuleList;
    }
}
