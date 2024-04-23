package com.example.models;

public class Appointment {
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userDate;

    private String userTime;
    private String userContent;

    public Appointment(String userName, String userPhone, String userAddress, String userDate, String userTime, String userContent) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userDate = userDate;
        this.userTime = userTime;
        this.userContent = userContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }
}
