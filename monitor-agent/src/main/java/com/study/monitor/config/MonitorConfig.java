package com.study.monitor.config;

import com.study.monitor.accessor.MonitorCenterResource;
import com.study.monitor.dto.MonitorRulesDTO;
import com.study.monitor.dto.ServerRulesDTO;
import com.study.monitor.monitor.impl.ProcessMonitor;
import com.study.monitor.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class MonitorConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMonitor.class);

    private final MonitorCenterResource resource;
    private final Duration ruleFetchInterval;

    @Autowired
    public MonitorConfig(MonitorCenterResource resource,
                         @Value("${monitor.rule.fetch.interval}") Duration ruleFetchInterval){
        this.resource = resource;
        this.ruleFetchInterval = ruleFetchInterval;
    }

    @Bean
    public ServerRulesDTO serverRules() {
        AtomicReference<MonitorRulesDTO> monitorRulesDTORef = new AtomicReference<>(resource.fetchMonitorRules());
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            LOGGER.info("Fetch monitor rules from monitor-center...");
            monitorRulesDTORef.set(resource.fetchMonitorRules());
        }, 0, ruleFetchInterval.toMillis(), TimeUnit.MILLISECONDS);
        MonitorRulesDTO monitorRulesDTO = monitorRulesDTORef.get();
        if (monitorRulesDTO != null && monitorRulesDTO.getServerRulesDTOList() != null){
            return monitorRulesDTO.getServerRulesDTOList().stream()
                    .filter(serverRulesDTO -> serverRulesDTO.getServer().getHostname().equalsIgnoreCase(HttpUtil.getCurrentHostname()))
                    .findFirst().orElseThrow(()->{
                        return new IllegalArgumentException("No current server config");
                    });
        } else {
            throw new IllegalArgumentException("No current server config");
        }
    }

}
