package com.study.monitor.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.monitor.service.ServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AgentHealthCheckController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentHealthCheckController.class);

    private final RestTemplate restTemplate;
    private final ServerService serverService;

    public AgentHealthCheckController(RestTemplate restTemplate, ServerService serverService){
        this.restTemplate = restTemplate;
        this.serverService = serverService;
    }

    @GetMapping(value = "/agent-health-check", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> checkAgentHealth(){
        List<String> hostnames = serverService.getAgentHostNameList();
        return Flux.interval(Duration.ZERO, Duration.ofSeconds(10))
                .map(tick -> {
                    Map<String, String> healthStatus = new HashMap<>();
                    for(String hostname : hostnames) {
                        String url = "http://" + hostname + ":8080/actuator/health";
                        ResponseEntity<String> response;
                        try {
                            response = restTemplate.getForEntity(url, String.class);
                            healthStatus.put(hostname, response.getStatusCode().is2xxSuccessful() ? "UP" : "DOWN");
                        } catch (Exception e){
                            LOGGER.debug("fail to call agent health check endpoint", e);
                            healthStatus.put(hostname, "DOWN");
                        }
                    }
                    return healthStatus;
                })
                .map(healthStatus -> {
                    List<Map<String, String>> statusList = new ArrayList<>();
                    for(Map.Entry<String, String> entry : healthStatus.entrySet()) {
                        Map<String, String> statusMap = new HashMap<>();
                        statusMap.put("hostname", entry.getKey());
                        statusMap.put("status", entry.getValue());
                        statusList.add(statusMap);
                    }
                    try {
                        return new ObjectMapper().writeValueAsString(statusList);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException("Failed to convert status list to JSON", e);
                    }
                });
    }
}
