package com.study.monitor.dto;


import com.study.monitor.entity.LogMonitoringRuleEntity;
import com.study.monitor.entity.ProcessMonitoringRuleEntity;

import java.util.ArrayList;
import java.util.List;

public class MonitorRulesDTO {

    private List<ServerRulesDTO> serverRulesDTOList;

    public List<ServerRulesDTO> getServerRulesDTOList() {
        return serverRulesDTOList;
    }

    public void setServerRulesDTOList(List<ServerRulesDTO> serverRulesDTOList) {
        this.serverRulesDTOList = serverRulesDTOList;
    }
}
