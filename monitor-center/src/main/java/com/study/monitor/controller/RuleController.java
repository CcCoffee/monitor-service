package com.study.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.dto.ServerRulesDTO;
import com.study.monitor.entity.MonitoringRuleEntity;
import com.study.monitor.entity.ServerEntity;
import com.study.monitor.entity.ServerMonitoringRuleEntity;
import com.study.monitor.service.MonitoringRuleService;
import com.study.monitor.service.ServerMonitoringRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RuleController {

    private final MonitoringRuleService service;
    private final ServerMonitoringRuleService serverMonitoringRuleService;

    @Autowired
    public RuleController(MonitoringRuleService service, ServerMonitoringRuleService serverMonitoringRuleService){
        this.service = service;
        this.serverMonitoringRuleService = serverMonitoringRuleService;
    }

    @RequestMapping("/rules")
    public IPage<MonitoringRuleEntity> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<MonitoringRuleEntity> page = new Page<>(pageNum, pageSize);
        return service.selectPage(page);
    }

    @RequestMapping({"/server-rules"})
    public List<ServerRulesDTO> getAllServerRules(){
        return service.getAllServerRules();
    }

    @PostMapping("/rules")
    public boolean save(@RequestBody MonitoringRuleEntity rule) {
        return service.saveOrUpdateEntity(rule);
    }

    @GetMapping("/rules/{ruleId}")
    public MonitoringRuleEntity selectWithServersById(@PathVariable(name = "ruleId") Integer ruleId){
        return service.selectWithServersById(ruleId);
    }

    @DeleteMapping("/rules/{ruleId}")
    public boolean deleteById(@PathVariable(name = "ruleId") Integer ruleId){
        return service.deleteById(ruleId);
    }
}
