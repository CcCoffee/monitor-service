package com.study.monitor.accessor;

import com.study.monitor.modal.dto.ServerRulesDTO;
import com.study.monitor.modal.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("monitor-center")
public interface MonitorCenterResource {

    @RequestMapping(value = "/server-rules", consumes = "application/json")
    ApiResponse<List<ServerRulesDTO>> fetchMonitorRules();

}
