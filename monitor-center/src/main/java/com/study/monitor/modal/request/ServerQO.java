package com.study.monitor.modal.request;

public class ServerQO {

    private Integer ruleId;
    private String serverNameFilter;
    private String hostnameFilter;

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    public String getServerNameFilter() {
        return serverNameFilter;
    }

    public void setServerNameFilter(String serverNameFilter) {
        this.serverNameFilter = serverNameFilter;
    }

    public String getHostnameFilter() {
        return hostnameFilter;
    }

    public void setHostnameFilter(String hostnameFilter) {
        this.hostnameFilter = hostnameFilter;
    }
}
