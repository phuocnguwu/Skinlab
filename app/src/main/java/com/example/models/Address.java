package com.example.models;

public class Address {
    private String name;
    private String phone;
    private String address;
    private String address2;

    public Address(String name, String phone, String address, String address2) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.address2 = address2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }
}
