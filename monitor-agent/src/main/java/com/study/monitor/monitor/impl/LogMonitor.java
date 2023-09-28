package com.study.monitor.monitor.impl;

import com.study.monitor.domain.LogMonitoringRule;
import com.study.monitor.dto.MonitorRulesDTO;
import com.study.monitor.monitor.Monitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class LogMonitor implements Monitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMonitor.class);

    private final MonitorRulesDTO monitorRulesDTO;

    public LogMonitor(MonitorRulesDTO monitorRulesDTO){
        this.monitorRulesDTO = monitorRulesDTO;
    }

    @Override
    public void monitor() {
        List<LogMonitoringRule> ruleList = monitorRulesDTO.getLogMonitoringRuleList();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ruleList.forEach(rule->{
            Path logFile = Path.of(rule.getLogFilePath());
            List<String> logPatterns = rule.getLogPatterns();
            AtomicLong lastLineCount = null;
            try {
                lastLineCount = new AtomicLong(Files.lines(logFile).count());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            AtomicLong finalLastLineCount = lastLineCount;
            executorService.scheduleAtFixedRate(() -> {
                try {
                    long linesToSkip = Math.max(0, finalLastLineCount.get());
                    AtomicBoolean match = new AtomicBoolean(false);
                    Files.lines(logFile)
                            .skip(linesToSkip)
                            .forEach(line -> {
                                for (String pattern : logPatterns) {
                                    if (line.matches(pattern)) {
                                        LOGGER.warn("Log pattern matched, rule: {}, raw message: {}", pattern, line);
                                        match.compareAndSet(false, true);
                                        break;
                                    }
                                }
                            });
                    finalLastLineCount.set(Files.lines(logFile).count());
                    if (match.get()) {
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
            }, 0, 5, TimeUnit.SECONDS);

        });
    }

    private void sendAlert(LogMonitoringRule rule) {
        LOGGER.error("xMatters, rule: {}", rule);
    }
}


