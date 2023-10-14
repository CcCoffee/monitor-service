package com.study.monitor.modal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.study.monitor.handler.JsonTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.Map;

@TableName(value = "notification", autoResultMap = true)
public class NotificationEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("alert_id")
    private Integer alertId;

    @TableField("channel_id")
    private Integer channelId;

    /**
     * EmailNotificationContent
     */
    @TableField(value = "content", typeHandler = JsonTypeHandler.class, jdbcType = JdbcType.OTHER)
    private Map<String, Object> content;

    @TableField("notification_time")
    private Date notificationTime;

    @TableField("response_code")
    private Integer responseCode;

    @TableField("response_message")
    private String responseMessage;

    @TableField("create_date")
    private Date createDate;

    @TableField("update_date")
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public void setContent(Map<String, Object> content) {
        this.content = content;
    }

    public Date getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Date notificationTime) {
        this.notificationTime = notificationTime;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
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

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "id=" + id +
                ", alertId=" + alertId +
                ", channelId=" + channelId +
                ", content=" + content +
                ", notificationTime=" + notificationTime +
                ", responseCode=" + responseCode +
                ", responseMessage='" + responseMessage + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
