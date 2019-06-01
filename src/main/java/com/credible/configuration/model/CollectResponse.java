package com.credible.configuration.model;

public class CollectResponse {
    private String orderRef;
    private String status;
    private String hintCode;
    private CompletitionData completitionData;

    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHintCode() {
        return hintCode;
    }

    public void setHintCode(String hintCode) {
        this.hintCode = hintCode;
    }

    public CompletitionData getCompletitionData() {
        return completitionData;
    }

    public void setCompletitionData(CompletitionData completitionData) {
        this.completitionData = completitionData;
    }
}
