package com.study.monitor.service.impl;

import com.study.monitor.dto.MonitorRulesDTO;
import com.study.monitor.dto.ServerRulesDTO;
import com.study.monitor.mapper.ServerMapper;
import com.study.monitor.service.MonitorRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorRuleServiceImpl implements MonitorRuleService {

    private final ServerMapper serverMapper;

    @Autowired
    public MonitorRuleServiceImpl(ServerMapper serverMapper){
        this.serverMapper = serverMapper;
    }

    @Override
    public MonitorRulesDTO getAllRules() {
        MonitorRulesDTO monitorRulesDTO = new MonitorRulesDTO();
        List<ServerRulesDTO> serverRules = serverMapper.selectServerRules();
        monitorRulesDTO.setServerRulesDTOList(serverRules);
        return monitorRulesDTO;
    }
}
