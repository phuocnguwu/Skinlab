package com.example.models;

public class Account {
    private String hoTen;
    private String sdt;
    private String email;
    private String matKhau;
    private String dob;
    private String gioiTinh;

    public Account(String hoTen, String sdt, String email, String matKhau, String dob, String gioiTinh) {
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.email = email;
        this.matKhau = matKhau;
        this.dob = dob;
        this.gioiTinh = gioiTinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }
}



