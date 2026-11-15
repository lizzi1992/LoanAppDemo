package com.example.loanappdemo.model;

public class LoanProduct {
    private String productId;
    private String productName;
    private double minAmount;
    private double maxAmount;
    private double interestRate;
    private int term;
    private int status; // 0-下架, 1-上架

    public LoanProduct() {}

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getMinAmount() { return minAmount; }
    public void setMinAmount(double minAmount) { this.minAmount = minAmount; }

    public double getMaxAmount() { return maxAmount; }
    public void setMaxAmount(double maxAmount) { this.maxAmount = maxAmount; }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    public int getTerm() { return term; }
    public void setTerm(int term) { this.term = term; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
