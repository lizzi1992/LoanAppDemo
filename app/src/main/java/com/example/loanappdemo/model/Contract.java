package com.example.loanappdemo.model;

public class Contract {
    private String contractId;
    private String applyId;
    private double contractAmount;
    private String signTime;
    private int status; // 0-未签, 1-已签

    public Contract() {}

    public String getContractId() { return contractId; }
    public void setContractId(String contractId) { this.contractId = contractId; }

    public String getApplyId() { return applyId; }
    public void setApplyId(String applyId) { this.applyId = applyId; }

    public double getContractAmount() { return contractAmount; }
    public void setContractAmount(double contractAmount) { this.contractAmount = contractAmount; }

    public String getSignTime() { return signTime; }
    public void setSignTime(String signTime) { this.signTime = signTime; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
