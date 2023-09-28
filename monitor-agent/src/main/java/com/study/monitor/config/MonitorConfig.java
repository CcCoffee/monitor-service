package com.study.monitor.config;

import com.study.monitor.accessor.MonitorCenterResource;
import com.study.monitor.dto.MonitorRulesDTO;
import com.study.monitor.monitor.impl.ProcessMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class MonitorConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMonitor.class);

    private final MonitorCenterResource resource;
    private final long fetchIntervalInMinutes;

    @Autowired
    public MonitorConfig(MonitorCenterResource resource, @Value("${monitor.rule.fetch-interval-in-minutes}") long fetchIntervalInMinutes){
        this.resource = resource;
        this.fetchIntervalInMinutes = fetchIntervalInMinutes;
    }

    @Bean
    public MonitorRulesDTO monitorRules() {
        AtomicReference<MonitorRulesDTO> monitorRulesDTO = new AtomicReference<>(resource.fetchMonitorRules());
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            LOGGER.info("Fetch monitor rules from monitor-center...");
            monitorRulesDTO.set(resource.fetchMonitorRules());
        }, 0, fetchIntervalInMinutes, TimeUnit.MINUTES);
        return monitorRulesDTO.get();
    }

}
