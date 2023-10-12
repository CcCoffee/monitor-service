package com.study.monitor.modal.json;

import java.util.List;

/**
 * 作为 channel 的 configuration
 */
public class EmailChannelConfiguration {
    private List<String> emailAddresses;
    private String emailTemplate;

    // Getters and Setters

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }
}