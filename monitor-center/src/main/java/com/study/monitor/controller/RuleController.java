package com.study.monitor.controller;

import com.study.monitor.dto.MonitorRulesDTO;
import com.study.monitor.service.MonitorRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rules")
public class RuleController {

    private final MonitorRuleService service;

    @Autowired
    public RuleController(MonitorRuleService service){
        this.service = service;
    }

    @RequestMapping({"", "/"})
    public MonitorRulesDTO list(){
        return service.getAllRules();
    }

}
