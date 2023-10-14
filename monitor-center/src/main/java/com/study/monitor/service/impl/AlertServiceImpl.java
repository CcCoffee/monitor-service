package com.study.monitor.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.monitor.mapper.AlertMapper;
import com.study.monitor.mapper.MonitoringRuleMapper;
import com.study.monitor.modal.entity.AlertEntity;
import com.study.monitor.modal.entity.MonitoringRuleEntity;
import com.study.monitor.modal.entity.ServerEntity;
import com.study.monitor.modal.request.AlertQO;
import com.study.monitor.service.AlertService;
import com.study.monitor.service.MonitoringRuleService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
        Date now = new Date();
        if (existingAlerts.isEmpty()) {
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
        } else {
            existingAlerts = existingAlerts.stream().peek(item -> {
                item.setRepeatCount(item.getRepeatCount() + 1);
                item.setEndTime(now);
            }).collect(Collectors.toList());
            return this.updateBatchById(existingAlerts);
        }
    }

    @Override
    public List<String> getAllApplicationName(AlertQO alertQO) {
        return this.baseMapper.getAllApplicationName(alertQO);
    }
}
