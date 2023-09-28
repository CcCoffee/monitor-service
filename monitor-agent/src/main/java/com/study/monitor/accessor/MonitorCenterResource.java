package com.study.monitor.accessor;

import com.study.monitor.dto.MonitorRulesDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("monitor-center")
public interface MonitorCenterResource {

    @RequestMapping(value = "/rules", consumes = "application/json")
    MonitorRulesDTO fetchMonitorRules();

}
