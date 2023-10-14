package com.study.monitor.modal.json;

import java.util.List;

public class EmailNotificationContent {

    private List<String> toEmailAddressList;
    private List<String> ccEmailAddressList;
    private String subject;
    private String content;

    public List<String> getToEmailAddressList() {
        return toEmailAddressList;
    }

    public void setToEmailAddressList(List<String> toEmailAddressList) {
        this.toEmailAddressList = toEmailAddressList;
    }

    public List<String> getCcEmailAddressList() {
        return ccEmailAddressList;
    }

    public void setCcEmailAddressList(List<String> ccEmailAddressList) {
        this.ccEmailAddressList = ccEmailAddressList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
