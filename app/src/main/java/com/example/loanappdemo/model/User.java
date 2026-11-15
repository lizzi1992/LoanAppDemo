package com.example.loanappdemo.model;

public class User {
    private String userId;
    private String userName;
    private String phone;
    private String idCard;
    private int status; // 0-正常, 1-冻结

    public User() {}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getIdCard() { return idCard; }
    public void setIdCard(String idCard) { this.idCard = idCard; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
}
