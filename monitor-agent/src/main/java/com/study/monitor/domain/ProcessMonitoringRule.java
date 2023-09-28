package com.study.monitor.domain;

import java.util.Date;
import java.util.List;

public class ProcessMonitoringRule {

    private Integer id;
    // 监控规则的名称或标识符
    private String name;
    // 监控规则的描述，用于说明该规则的作用和目的。
    private String description;
    // 指示监控规则是否启用的标志。
    private boolean enabled;
    // 监控规则的阈值，用于确定何时触发警报或采取其他操作。
    private int threshold;
    // 监控规则执行的时间间隔，即监控频率。
    private int interval;
    // 接收警报通知的用户或团队的列表。
    private List<String> notificationRecipients;
    // 要监控的进程的正则表达式
    private String processNameRegex;
    // 创建该监控规则的用户或系统的标识符。
    private String createdBy;
    // 监控规则的创建时间戳
    private Date createdDate;
    private Date lastAlertTime;

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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastAlertTime() {
        return lastAlertTime;
    }

    public void setLastAlertTime(Date lastAlertTime) {
        this.lastAlertTime = lastAlertTime;
    }

    @Override
    public String toString() {
        return "ProcessMonitoringRule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", enabled=" + enabled +
                ", threshold=" + threshold +
                ", interval=" + interval +
                ", notificationRecipients=" + notificationRecipients +
                ", processNameRegex='" + processNameRegex + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastAlertTime=" + lastAlertTime +
                '}';
    }
}
