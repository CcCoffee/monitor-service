package com.study.monitor.modal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.study.monitor.handler.PgArrayStringTypeHandler;

import java.util.Date;
import java.util.List;

@TableName(value = "monitoring_rule", autoResultMap = true)
public class MonitoringRuleEntity {

    @TableId(type = IdType.AUTO)
    private Integer id;
    // 监控规则的名称或标识符
    private String name;
    // PROCESS / LOG
    private String type;
    private String application;
    // 监控规则的描述，用于说明该规则的作用和目的。
    private String description;
    // 指示监控规则是否启用的标志。
    private boolean enabled;
    // 监控规则的阈值，用于确定何时触发警报或采取其他操作。
    private int threshold;
    // 监控规则执行的时间间隔，即监控频率。
    private int interval;
    // 接收警报通知的用户或团队的列表。
    @TableField(typeHandler = PgArrayStringTypeHandler.class)
    private List<String> notificationRecipients;
    // 要监控的进程的正则表达式
    private String processNameRegex;
    // 要监控的日志文件地址
    private String logFilePath;
    // 要监控的日志规则
    @TableField(typeHandler = PgArrayStringTypeHandler.class)
    private List<String> logPatterns;
    private String severity;
    // 创建该监控规则的用户或系统的标识符。
    private String createdBy;
    private String updatedBy;
    // 监控规则的创建时间戳
    private Date createDate;
    private Date updateDate;
    @TableField(exist = false)
    private Date lastAlertTime;
    @TableField(exist = false)
    private List<ServerEntity> linkedServers;
    @TableField(exist = false)
    private List<Integer> channelIds;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<String> getNotificationRecipients() {
        return notificationRecipients;
    }

    public void setNotificationRecipients(List<String> notificationRecipients) {
        this.notificationRecipients = notificationRecipients;
    }

    public String getProcessNameRegex() {
        return processNameRegex;
    }

    public void setProcessNameRegex(String processNameRegex) {
        this.processNameRegex = processNameRegex;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public List<String> getLogPatterns() {
        return logPatterns;
    }

    public void setLogPatterns(List<String> logPatterns) {
        this.logPatterns = logPatterns;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
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

    public Date getLastAlertTime() {
        return lastAlertTime;
    }

    public void setLastAlertTime(Date lastAlertTime) {
        this.lastAlertTime = lastAlertTime;
    }

    public List<ServerEntity> getLinkedServers() {
        return linkedServers;
    }

    public List<Integer> getChannelIds() {
        return channelIds;
    }

    public void setChannelIds(List<Integer> channelIds) {
        this.channelIds = channelIds;
    }

    public void setLinkedServers(List<ServerEntity> linkedServers) {


        this.linkedServers = linkedServers;
    }
}
