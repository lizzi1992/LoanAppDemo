package com.example.loanappdemo.model;

public class LoanApplication {
    private String applyId;
    private String userId;
    private String productId;
    private double applyAmount;
    private int applyTerm;
    private String status; // 待审核, 已通过, 已拒绝, 退回补充
    private String applyTime;

    public LoanApplication() {}

    public String getApplyId() { return applyId; }
    public void setApplyId(String applyId) { this.applyId = applyId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public double getApplyAmount() { return applyAmount; }
    public void setApplyAmount(double applyAmount) { this.applyAmount = applyAmount; }

    public int getApplyTerm() { return applyTerm; }
    public void setApplyTerm(int applyTerm) { this.applyTerm = applyTerm; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getApplyTime() { return applyTime; }
    public void setApplyTime(String applyTime) { this.applyTime = applyTime; }
}
