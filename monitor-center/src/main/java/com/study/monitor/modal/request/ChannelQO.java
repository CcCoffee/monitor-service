package com.study.monitor.modal.request;

public class ChannelQO {
    private String nameFilter;
    private String typeFilter;
    private Integer ruleId;
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

    public Integer getRuleId() {
        return ruleId;
    }

    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }
}
