package com.study.monitor.service.impl;

import com.study.monitor.dto.MonitorRulesDTO;
import com.study.monitor.entity.LogMonitoringRuleEntity;
import com.study.monitor.entity.ProcessMonitoringRuleEntity;
import com.study.monitor.service.MonitorRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorRuleServiceImpl implements MonitorRuleService {
    @Override
    public MonitorRulesDTO getAllRules() {
        LogMonitoringRuleEntity logRule1 = new LogMonitoringRuleEntity();
        logRule1.setId(1);
        logRule1.setDescription("log rule 1");
        logRule1.setName("logRule1");
        logRule1.setLogFilePath("test.log");
        logRule1.setLogPatterns(List.of(".*Error.*"));
        logRule1.setNotificationRecipients(List.of("kevin.k.h.zheng"));
        logRule1.setThreshold(5);
        logRule1.setInterval(5000);

        ProcessMonitoringRuleEntity processRule1 = new ProcessMonitoringRuleEntity();
        processRule1.setId(1);
        processRule1.setDescription("process rule 1");
        processRule1.setName("processRule1");
        processRule1.setProcessNameRegex(".*notepad.*");
        processRule1.setNotificationRecipients(List.of("kevin.k.h.zheng"));
        processRule1.setThreshold(5);
        processRule1.setInterval(5000);

        MonitorRulesDTO monitorRulesDTO = new MonitorRulesDTO();
        monitorRulesDTO.setLogMonitoringRuleList(List.of(logRule1));
        monitorRulesDTO.setProcessMonitoringRuleList(List.of(processRule1));
        return monitorRulesDTO;
    }
}
