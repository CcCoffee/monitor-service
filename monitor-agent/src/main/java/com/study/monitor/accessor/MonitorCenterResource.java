package com.study.monitor.accessor;

import com.study.monitor.dto.MonitorRulesDTO;
import com.study.monitor.dto.ServerRulesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("monitor-center")
public interface MonitorCenterResource {

    @RequestMapping(value = "/rules", consumes = "application/json")
    List<ServerRulesDTO> fetchMonitorRules();

}
