package com.study.monitor.monitor.impl;

import com.study.monitor.accessor.MonitorCenterResource;
import com.study.monitor.domain.MonitoringRule;
import com.study.monitor.exception.BreakLoopException;
import com.study.monitor.modal.dto.AlertDTO;
import com.study.monitor.modal.dto.ServerRulesDTO;
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
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class LogMonitor implements Monitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessMonitor.class);

    private final ServerRulesDTO serverRulesDTO;
    private final Duration coolDownTime;
    private final Duration monitoringInterval;
    private final MonitorCenterResource monitorCenterResource;

    @Autowired
    public LogMonitor(ServerRulesDTO serverRulesDTO,
                      @Value("${monitor.log.alert.cool-down-time}") Duration coolDownTime,
                      @Value("${monitor.log.monitoring.interval}") Duration monitoringInterval,
                      MonitorCenterResource monitorCenterResource) {
        this.serverRulesDTO = serverRulesDTO;
        this.coolDownTime = coolDownTime;
        this.monitoringInterval = monitoringInterval;
        this.monitorCenterResource = monitorCenterResource;
    }

    @Override
    public void monitor() {
        List<MonitoringRule> ruleList = serverRulesDTO.getMonitoringRuleList().stream().filter(rule->rule.getType().equals("LOG")).collect(Collectors.toList());;
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
                logToLastAlertTimeMap.entrySet().removeIf(entry -> entry.getValue().getTime() + coolDownTime.toMillis() < System.currentTimeMillis());
                try {
                    long linesToSkip = Math.max(0, finalLastLineCount.get());
                    AtomicBoolean matchPattern = new AtomicBoolean(false);
                    AtomicReference<List<String>> matchedLogList = new AtomicReference<>(new ArrayList<>());

                    AtomicLong outerAdditionalLinesToSkip = new AtomicLong(0);
                    AtomicLong innerLoopAdditionalLinesToSkip = new AtomicLong(0);
                    Files.lines(logFile)
                            .skip(linesToSkip)
                            .forEach(line -> {
                                outerAdditionalLinesToSkip.incrementAndGet();
                                for (String pattern : logPatterns) {
                                    if (line.matches(pattern)) {
                                        innerLoopAdditionalLinesToSkip.set(outerAdditionalLinesToSkip.get());
//                                        LOGGER.warn("Log pattern matched, rule: {}, raw message: {}", pattern, line);
                                        matchPattern.compareAndSet(false, true);

                                        StringBuffer exceptionLogSnippet = new StringBuffer();
                                        exceptionLogSnippet.append(line);
                                        try {
                                            String logDateTimePattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}.*";
                                            Files.lines(logFile)
                                                    .skip(linesToSkip + innerLoopAdditionalLinesToSkip.getAndIncrement())
                                                    .forEach(l -> {
                                                        if (!l.matches(logDateTimePattern)) {
                                                            exceptionLogSnippet.append(l).append("\n");
                                                        } else {
                                                            throw new BreakLoopException();
                                                        }
                                                    });
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        } catch (BreakLoopException ignored){
                                            innerLoopAdditionalLinesToSkip.set(outerAdditionalLinesToSkip.get());
                                        }
                                        List<String> logList = matchedLogList.get();
                                        logList.add(exceptionLogSnippet.toString());
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
                                    LOGGER.debug(">>>>> Not first time met the log in {} minutes alert cool down  time window, ignore sending alert.\n>>>>>>>>>>>>>>> baselineLog: {}\n>>>>>>>>>>>>>>> matchedLog:{}", coolDownTime.toMinutes(), baselineLog, matchedLog);
                                } else { // not similar log exist
                                    LOGGER.warn(">>>>> No similar log met in {} minutes alert cool down time window.\n>>>>>>>>>>>>>>> matchPattern: {}\n>>>>>>>>>>>>>>> matchedLog: {}", coolDownTime.toMinutes(), matchPattern.get(), matchedLog);
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
            }, 0, monitoringInterval.toSeconds(), TimeUnit.SECONDS);

        });
    }

    private void sendAlert(MonitoringRule rule, String matchedLog) {
        LOGGER.error(">>>>> xMatters\n<<<<<<<<<<<< Content: {}", matchedLog);
        AlertDTO alert = new AlertDTO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String alertReason = "Log matched";
        alert.setName(String.format("[%s][%s][%s] - %s", simpleDateFormat.format(new Date()), serverRulesDTO.getHostname(), rule.getApplication(), alertReason));
        alert.setContent(matchedLog);
        alert.setHostname(serverRulesDTO.getHostname());
        alert.setRuleId(rule.getId());
        monitorCenterResource.createAlert(alert);
    }
}
