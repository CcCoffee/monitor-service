package com.study.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.monitor.dto.ServerRulesDTO;
import com.study.monitor.entity.MonitoringRuleEntity;
import com.study.monitor.service.MonitoringRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RuleController {

    private final MonitoringRuleService service;

    @Autowired
    public RuleController(MonitoringRuleService service){
        this.service = service;
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

}
