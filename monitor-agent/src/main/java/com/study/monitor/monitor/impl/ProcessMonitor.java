package com.study.monitor.monitor.impl;

import com.study.monitor.dto.MonitorRulesDTO;
import com.study.monitor.domain.ProcessMonitoringRule;
import com.study.monitor.monitor.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ProcessMonitor implements Monitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMonitor.class);

    private final MonitorRulesDTO monitorRulesDTO;

    public ProcessMonitor(MonitorRulesDTO monitorRulesDTO){
        this.monitorRulesDTO = monitorRulesDTO;
    }

    @Override
    public void monitor() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            List<ProcessMonitoringRule> ruleList = monitorRulesDTO.getProcessMonitoringRuleList(); // Retrieve the monitoring rule for this log
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
                        LOGGER.warn("Process not found, sending alert...");
                        // Check if enough time has passed since the last alert
                        Date currentTime = new Date();
                        if (rule.getLastAlertTime() == null || currentTime.getTime() - rule.getLastAlertTime().getTime() >= 10 * 60 * 1000) {
                            // Enough time has passed, trigger the alert
                            rule.setLastAlertTime(currentTime);
                            sendAlert(rule); // Add your alert logic here
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }, 0, 5, TimeUnit.SECONDS);
    }

    private void sendAlert(ProcessMonitoringRule rule) {
        LOGGER.error("xMatters, rule: {}", rule);
    }
}
