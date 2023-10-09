package com.study.monitor.monitor.impl;

import com.study.monitor.domain.MonitoringRule;
import com.study.monitor.modal.dto.ServerRulesDTO;
import com.study.monitor.monitor.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class ProcessMonitor implements Monitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMonitor.class);

    private final ServerRulesDTO serverRulesDTO;
    private final Duration coolDownTime;
    private final Duration heartbeatInterval;

    @Autowired
    public ProcessMonitor(ServerRulesDTO serverRulesDTO,
                          @Value("${monitor.process.alert.cool-down-time}") Duration coolDownTime,
                          @Value("${monitor.process.monitoring.interval}") Duration heartbeatInterval){
        this.serverRulesDTO = serverRulesDTO;
        this.coolDownTime = coolDownTime;
        this.heartbeatInterval = heartbeatInterval;
    }

    @Override
    public void monitor() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            List<MonitoringRule> ruleList = serverRulesDTO.getMonitoringRuleList().stream().filter(rule-> rule.getType().equals("PROCESS")).collect(Collectors.toList()); // Retrieve the monitoring rule for this log
            ruleList.forEach(rule->{
                String os = System.getProperty("os.name").toLowerCase();

                try {
                    Process process;
                    if (os.contains("win")) {
                        process = new ProcessBuilder("tasklist").start();
                    } else {
                        process = new ProcessBuilder("ps", "-ef").start();
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                    String line;
                    boolean processFound = false;
                    while ((line = reader.readLine()) != null) {
                        if (line.matches(rule.getProcessNameRegex())) {
                            // Process is alive
                            processFound = true;
                            LOGGER.debug("Process is alive: " + line);
                        }
                    }

                    reader.close();
                    if (!processFound) {
                        // Process not found, send alert
                        LOGGER.warn("Process '{}' not found, sending alert...", rule.getProcessNameRegex());
                        // Check if enough time has passed since the last alert
                        Date currentTime = new Date();
                        if (rule.getLastAlertTime() == null || currentTime.getTime() - rule.getLastAlertTime().getTime() >= coolDownTime.toMillis()) {
                            // Enough time has passed, trigger the alert
                            rule.setLastAlertTime(currentTime);
                            sendAlert(rule); // Add your alert logic here
                        } else {
                            LOGGER.debug("Process '{}' not found, but still in cool time down, ignore sending alert...", rule.getProcessNameRegex());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }, 0, heartbeatInterval.toMillis(), TimeUnit.MILLISECONDS);
    }

    private void sendAlert(MonitoringRule rule) {
        LOGGER.error("xMatters, rule: {}", rule);
    }
}
