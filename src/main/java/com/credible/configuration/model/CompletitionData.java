package com.credible.configuration.model;

public class CompletitionData {
    private BankIdUser user;
    private Device device;
    private Cert cert;
    private String signature;
    private String ocspResponse;

    public BankIdUser getUser() {
        return user;
    }

    public void setUser(BankIdUser user) {
        this.user = user;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Cert getCert() {
        return cert;
    }

    public void setCert(Cert cert) {
        this.cert = cert;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getOcspResponse() {
        return ocspResponse;
    }

    public void setOcspResponse(String ocspResponse) {
        this.ocspResponse = ocspResponse;
    }
}
