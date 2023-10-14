package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.mapper.AlertMapper;
import com.study.monitor.modal.entity.AlertEntity;
import com.study.monitor.modal.entity.MonitoringRuleEntity;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.modal.request.AlertQO;
import com.study.monitor.service.AlertService;
import com.study.monitor.service.MonitoringRuleService;
import com.study.monitor.util.LevenshteinUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertServiceImpl  extends ServiceImpl<AlertMapper, AlertEntity> implements AlertService {

    private final MonitoringRuleService monitoringRuleService;

    public AlertServiceImpl(MonitoringRuleService monitoringRuleService) {
        this.monitoringRuleService = monitoringRuleService;
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
            return populateAndInsertAlert(alert, rule);
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
                return this.lambdaUpdate().in(AlertEntity::getId, similarAlerts.stream().map(AlertEntity::getId).collect(Collectors.toSet()))
                        .setSql("repeat_count = repeat_count + 1")
                        .set(AlertEntity::getEndTime, now)
                        .update();
//                return this.updateBatchById(similarAlerts);
            } else {
                // new record
                return populateAndInsertAlert(alert, rule);
            }
        }
    }

    private boolean populateAndInsertAlert(AlertEntity alert, MonitoringRuleEntity rule) {
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
        return this.save(alert);
    }

    @Override
    public List<String> getAllApplicationName(AlertQO alertQO) {
        return this.baseMapper.getAllApplicationName(alertQO);
    }
}
