package com.study.monitor.controller;

import com.study.monitor.dto.ServerRulesDTO;
import com.study.monitor.service.MonitoringRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final MonitoringRuleService service;

    @Autowired
    public RuleController(MonitoringRuleService service){
        this.service = service;
    }

    @RequestMapping({"", "/"})
    public List<ServerRulesDTO> list(){
        return service.getAllRules();
    }

}
