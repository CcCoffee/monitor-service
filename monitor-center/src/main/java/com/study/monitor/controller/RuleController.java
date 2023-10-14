package com.study.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.modal.dto.ServerRulesDTO;
import com.study.monitor.modal.entity.MonitoringRuleEntity;
import com.study.monitor.modal.request.RuleQO;
import com.study.monitor.modal.response.ApiResponse;
import com.study.monitor.service.MonitoringRuleService;
import com.study.monitor.service.ServerMonitoringRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<IPage<MonitoringRuleEntity>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                            RuleQO ruleQO) {
        Page<MonitoringRuleEntity> page = new Page<>(pageNum, pageSize);
//        RuleQO ruleQO = new RuleQO();
//        ruleQO.setNameFilter(nameFilter);
//        ruleQO.setTypeFilter(typeFilter);
//        ruleQO.setServerFilter(serverFilter);
        return ApiResponse.success(service.selectMyPage(page, ruleQO));
    }

    @RequestMapping({"/server-rules"})
    public ApiResponse<List<ServerRulesDTO>> getAllServerRules(){
        return ApiResponse.success(service.getAllServerRules());
    }

    @RequestMapping({"/rules/applications"})
    public ApiResponse<List<String>> getAllApplicationName(RuleQO ruleQO){
//        RuleQO ruleQO = new RuleQO();
//        ruleQO.setNameFilter(nameFilter);
//        ruleQO.setTypeFilter(typeFilter);
//        ruleQO.setServerFilter(serverFilter);
        return ApiResponse.success(service.getAllApplicationName(ruleQO));
    }

    @PostMapping("/rules")
    public ApiResponse<Boolean> save(@RequestBody MonitoringRuleEntity rule) {
        return ApiResponse.success(service.saveOrUpdateEntity(rule));
    }

    @GetMapping("/rules/{ruleId}")
    public ApiResponse<MonitoringRuleEntity> selectWithServersById(@PathVariable(name = "ruleId") Integer ruleId){
        return ApiResponse.success(service.selectWithServersById(ruleId));
    }

    @DeleteMapping("/rules/{ruleId}")
    public ApiResponse<Boolean> deleteById(@PathVariable(name = "ruleId") Integer ruleId){
        return ApiResponse.success(service.deleteById(ruleId));
    }
}
