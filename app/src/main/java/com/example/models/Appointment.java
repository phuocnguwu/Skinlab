package com.example.models;

public class Appointment {
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userDate;

    public Appointment(String userName, String userPhone, String userAddress, String userDate) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.userDate = userDate;
    }

    // Getter methods
    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getUserDate() {
        return userDate;
    }
}
