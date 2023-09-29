package com.study.monitor.dto;


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
