package com.study.monitor.modal.request;

public class RuleQO {

    private String nameFilter;
    private String typeFilter;
    private String applicationFilter;
    private Integer serverFilter;

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public String getTypeFilter() {
        return typeFilter;
    }

    public void setTypeFilter(String typeFilter) {
        this.typeFilter = typeFilter;
    }

    public String getApplicationFilter() {
        return applicationFilter;
    }

    public void setApplicationFilter(String applicationFilter) {
        this.applicationFilter = applicationFilter;
    }

    public Integer getServerFilter() {
        return serverFilter;
    }

    public void setServerFilter(Integer serverFilter) {
        this.serverFilter = serverFilter;
    }
}
