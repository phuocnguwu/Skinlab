package com.example.models;

public class Address {
    private String name;
    private String phone;
    private String province;
    private String district;
    private String address;
    private String province2;
    private String district2;
    private String address2;

    public Address(String name, String phone, String province, String district, String address, String province2, String district2, String address2) {
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.district = district;
        this.address = address;
        this.province2 = province2;
        this.district2 = district2;
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince2() {
        return province2;
    }

    public void setProvince2(String province2) {
        this.province2 = province2;
    }

    public String getDistrict2() {
        return district2;
    }

    public void setDistrict2(String district2) {
        this.district2 = district2;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }
}
