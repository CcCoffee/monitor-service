package com.study.monitor.monitor.impl;

import com.study.monitor.domain.LogMonitoringRule;
import com.study.monitor.dto.MonitorRulesDTO;
import com.study.monitor.monitor.Monitor;
import com.study.monitor.util.LevenshteinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class LogMonitor implements Monitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMonitor.class);

    private final MonitorRulesDTO monitorRulesDTO;
    private final Integer coolDownTimeInMinutes;

    @Autowired
    public LogMonitor(MonitorRulesDTO monitorRulesDTO, @Value("${monitor.log.alert.cool-down-time-in-minutes}") Integer coolDownTimeInMinutes) {
        this.monitorRulesDTO = monitorRulesDTO;
        this.coolDownTimeInMinutes = coolDownTimeInMinutes;
    }

    @Override
    public void monitor() {
        List<LogMonitoringRule> ruleList = monitorRulesDTO.getLogMonitoringRuleList();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ruleList.forEach(rule -> {
            Path logFile = Path.of(rule.getLogFilePath());
            List<String> logPatterns = rule.getLogPatterns();
            AtomicLong lastLineCount;
            try {
                lastLineCount = new AtomicLong(Files.lines(logFile).count());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            AtomicLong finalLastLineCount = lastLineCount;
            Map<String, Date> logToLastAlertTimeMap = new HashMap<>();
            executorService.scheduleAtFixedRate(() -> {
                // Remove expired logs
                logToLastAlertTimeMap.entrySet().removeIf(entry -> entry.getValue().getTime() + (coolDownTimeInMinutes * 60 * 1000) < System.currentTimeMillis());
                try {
                    long linesToSkip = Math.max(0, finalLastLineCount.get());
                    AtomicBoolean matchPattern = new AtomicBoolean(false);
                    AtomicReference<List<String>> matchedLogList = new AtomicReference<>(new ArrayList<>());

                    Files.lines(logFile)
                            .skip(linesToSkip)
                            .forEach(line -> {
                                for (String pattern : logPatterns) {
                                    if (line.matches(pattern)) {
//                                        LOGGER.warn("Log pattern matched, rule: {}, raw message: {}", pattern, line);
                                        matchPattern.compareAndSet(false, true);
                                        List<String> logList = matchedLogList.get();
                                        logList.add(line);
                                        matchedLogList.set(logList);
                                        break;
                                    }
                                }
                            });
                    finalLastLineCount.set(Files.lines(logFile).count());
                    if (matchPattern.get()) {
                        for (String matchedLog : matchedLogList.get()) {
                            if (logToLastAlertTimeMap.isEmpty()) { // First time met the log.
                                LOGGER.warn(">>>>> Empty map and the first time met error log pattern. Send alert!\n>>>>>>>>>>>>>>> matchedLog: {}", matchedLog);
                                Date currentTime = new Date();
                                logToLastAlertTimeMap.put(matchedLog, currentTime);
                                sendAlert(rule, matchedLog);
                            } else {
                                // is there any similar log before?
                                Optional<Map.Entry<String, Date>> matchLogEntryOpt = logToLastAlertTimeMap.entrySet().stream().filter(entry -> {
                                    String baselineLog = entry.getKey();
                                    return LevenshteinUtil.isSimilarLog(baselineLog, matchedLog);
                                }).findFirst();
                                if (matchLogEntryOpt.isPresent()) { // similar log exists, ignore send alert
                                    String baselineLog = matchLogEntryOpt.get().getKey();
                                    LOGGER.warn(">>>>> Not first time met the log in {} minutes alert cool down  time window, ignore sending alert.\n>>>>>>>>>>>>>>> baselineLog: {}\n>>>>>>>>>>>>>>> matchedLog:{}", coolDownTimeInMinutes, baselineLog, matchedLog);
                                } else { // not similar log exist
                                    LOGGER.warn(">>>>> No similar log met before in {} minutes alert cool down time window.\n>>>>>>>>>>>>>>> matchPattern: {}\n>>>>>>>>>>>>>>> matchedLog: {}", coolDownTimeInMinutes, matchPattern.get(), matchedLog);
                                    Date currentTime = new Date();
                                    logToLastAlertTimeMap.put(matchedLog, currentTime);
                                    sendAlert(rule, matchedLog);
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }, 0, 5, TimeUnit.SECONDS);

        });
    }

    private void sendAlert(LogMonitoringRule rule, String matchedLog) {
        LOGGER.error(">>>>> xMatters\n<<<<<<<<<<<< matchedLog: {}", matchedLog);
    }
}