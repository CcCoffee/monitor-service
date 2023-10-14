package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.monitor.mapper.AlertMapper;
import com.study.monitor.modal.entity.*;
import com.study.monitor.modal.json.EmailNotificationContent;
import com.study.monitor.modal.request.AlertQO;
import com.study.monitor.modal.request.ChannelQO;
import com.study.monitor.service.AlertService;
import com.study.monitor.service.ChannelService;
import com.study.monitor.service.MonitoringRuleService;
import com.study.monitor.service.NotificationService;
import com.study.monitor.util.LevenshteinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlertServiceImpl  extends ServiceImpl<AlertMapper, AlertEntity> implements AlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertServiceImpl.class);

    private final MonitoringRuleService monitoringRuleService;
    private final ChannelService channelService;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public AlertServiceImpl(MonitoringRuleService monitoringRuleService, ChannelService channelService,
                            NotificationService notificationService, ObjectMapper objectMapper) {
        this.monitoringRuleService = monitoringRuleService;
        this.channelService = channelService;
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }
    @Override
    public IPage<AlertEntity> selectMyPage(Page<ServerEntity> page, AlertQO alertQO) {
        return this.baseMapper.selectMyPage(page, alertQO);
    }

    @Override
    public Boolean saveOrUpdateEntity(AlertEntity alert) {
        List<AlertEntity> existingAlerts = lambdaQuery().eq(AlertEntity::getRuleId, alert.getRuleId()).in(AlertEntity::getStatus, List.of("Open", "Acknowledged")).list();
        MonitoringRuleEntity rule = monitoringRuleService.getById(alert.getRuleId());

        if (existingAlerts.isEmpty()) {
            populateAndInsertAlert(alert, rule);
            sendNotification(alert, rule);
            return true;
        } else {
            // 判断是否有类似的日志存在，存在则更新，不存在则新增
            List<AlertEntity> similarAlerts = existingAlerts.stream().filter(existingAlert -> {
                return LevenshteinUtil.isSimilarLog(existingAlert.getContent(), alert.getContent());
            }).collect(Collectors.toList());
            if (!similarAlerts.isEmpty()) {
                // update existing one
                Date now = new Date();
                similarAlerts = similarAlerts.stream().peek(item -> {
                    item.setRepeatCount(item.getRepeatCount() + 1);
                    item.setEndTime(now);
                }).collect(Collectors.toList());
                boolean update = this.lambdaUpdate().in(AlertEntity::getId, similarAlerts.stream().map(AlertEntity::getId).collect(Collectors.toSet()))
                        .setSql("repeat_count = repeat_count + 1")
                        .set(AlertEntity::getEndTime, now)
                        .update();
                // TODO send notification and save the notification entity
                similarAlerts.forEach(alertToBeNotified -> {
                    sendNotification(alertToBeNotified, rule);
                });
                return update;
            } else {
                // new record
                populateAndInsertAlert(alert, rule);
                sendNotification(alert, rule);
                // TODO send notification and save the notification entity
                 return true;
            }
        }
    }

    private void sendNotification(AlertEntity alert, MonitoringRuleEntity rule) {
        ChannelQO channelQO = new ChannelQO();
        channelQO.setRuleId(rule.getId());
        List<ChannelEntity> channelEntityList = channelService.findByParams(channelQO);
        channelEntityList.forEach(channelEntity -> {
            Map<String, Object> configuration = channelEntity.getConfiguration();
            String emailTemplate = ((String) configuration.get("emailTemplate"));
            List<String> emailAddressList = ((List<String>) configuration.get("emailAddress"));
            String emailContent = String.format(emailTemplate, rule.getName(), alert.getHostname(), rule.getApplication(), alert.getContent());
            EmailNotificationContent emailNotificationContent = new EmailNotificationContent();
            String subject = String.format("Server %s - Application %s Exception Detected", alert.getHostname(), rule.getApplication());
            emailNotificationContent.setSubject(subject);
            emailNotificationContent.setToEmailAddressList(emailAddressList);
            emailNotificationContent.setContent(emailContent);
            ResponseEntity<String> sendResult = sendEmail(emailNotificationContent);
            recordNotification(alert, channelEntity.getId(), channelEntity.getType(), sendResult, emailNotificationContent);
        });
    }

    private void recordNotification(AlertEntity alert, Integer channelId, String channelType, ResponseEntity<String> sendResult, EmailNotificationContent emailNotificationContent) {
        NotificationEntity notificationEntity = new NotificationEntity();
        Date now = new Date();
        notificationEntity.setNotificationTime(now);
        notificationEntity.setAlertId(alert.getId());
        notificationEntity.setChannelId(channelId);
        Map<String, Object> content = new LinkedHashMap<>();
        if(channelType.equals("EMAIL")) {
            content.put("toEmailAddressList", emailNotificationContent.getToEmailAddressList());
            content.put("ccEmailAddressList", emailNotificationContent.getCcEmailAddressList());
            content.put("subject", emailNotificationContent.getSubject());
            content.put("content", emailNotificationContent.getContent());
        } else {
            // TODO
        }
        notificationEntity.setContent(content);
        notificationEntity.setResponseCode(sendResult.getStatusCodeValue());
        notificationEntity.setResponseMessage(sendResult.getBody());
        notificationEntity.setCreateDate(now);
        notificationEntity.setUpdateDate(now);
        notificationService.save(notificationEntity);
        LOGGER.info("Record notification: {}", notificationEntity);
    }

    private static ResponseEntity<String> sendEmail(EmailNotificationContent emailNotificationContent) {
        LOGGER.error("==============================Email start=====================================");
        LOGGER.error("To: {}", emailNotificationContent.getToEmailAddressList().stream().reduce((a, b) -> a.concat(",").concat(b)).get());
        LOGGER.error("Subject: {}", emailNotificationContent.getSubject());
        LOGGER.error("\n");
        LOGGER.error("{}", emailNotificationContent.getContent());
        LOGGER.error("==============================Email stop=====================================");
        return ResponseEntity.ok().build();
    }

    private AlertEntity populateAndInsertAlert(AlertEntity alert, MonitoringRuleEntity rule) {
        Date now = new Date();
        alert.setId(null);
        alert.setStatus("Open");
        alert.setRepeatCount(0);
        alert.setStartTime(now);
        alert.setEndTime(now);
        alert.setApplication(rule.getApplication());
        alert.setType(rule.getType());
        alert.setSeverity(rule.getSeverity());
        alert.setCreateDate(now);
        alert.setUpdateDate(now);
        this.save(alert);
        return alert;
    }

    @Override
    public List<String> getAllApplicationName(AlertQO alertQO) {
        return this.baseMapper.getAllApplicationName(alertQO);
    }
}
