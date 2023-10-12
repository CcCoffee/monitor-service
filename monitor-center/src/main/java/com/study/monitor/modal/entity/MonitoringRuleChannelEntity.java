package com.study.monitor.modal.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName(value = "monitoring_rule_channel", autoResultMap = true)
public class MonitoringRuleChannelEntity {

    private Integer channelId;
    private Integer monitoringRuleId;
    private Date createDate;
    private Date updateDate;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
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
