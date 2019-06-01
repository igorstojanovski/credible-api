package com.credible.configuration.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequest implements BankIdEntity {
    private String personalNumber;
    private String endUserIp;
    private String userVisibleData;

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getEndUserIp() {
        return endUserIp;
    }

    public void setEndUserIp(String endUserIp) {
        this.endUserIp = endUserIp;
    }

    public String getUserVisibleData() {
        return userVisibleData;
    }

    public void setUserVisibleData(String userVisibleData) {
        this.userVisibleData = userVisibleData;
    }
}
