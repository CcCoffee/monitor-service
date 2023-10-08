package com.study.monitor.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName(value = "server_monitoring_rule", autoResultMap = true)
public class ServerMonitoringRuleEntity {

    private Integer serverId;
    private Integer monitoringRuleId;
    private Date createDate;
    private Date updateDate;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getMonitoringRuleId() {
        return monitoringRuleId;
    }

    public void setMonitoringRuleId(Integer monitoringRuleId) {
        this.monitoringRuleId = monitoringRuleId;
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
}
