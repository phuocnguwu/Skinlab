package com.example.models;

import android.widget.Adapter;

import java.io.Serializable;

public class Product implements Serializable {

    String pd_photo;
    String pd_id;
    String pd_name;
    int pd_price;
    int pd_price2;
    String pd_brand;
    String pd_cate;
    String pd_des;
    String pd_skintype;





    public Product(String pd_photo, String pd_id, String pd_name, int pd_price, int pd_price2, String pd_brand, String pd_cate, String pd_des, String pd_skintype) {
        this.pd_id = pd_id;
        this.pd_name = pd_name;
        this.pd_price = pd_price;
        this.pd_price2 = pd_price2;
        this.pd_brand = pd_brand;
        this.pd_cate = pd_cate;
        this.pd_des = pd_des;
        this.pd_skintype = pd_skintype;
        this.pd_photo = pd_photo;
    }

    public Product(String pdId, String pdName, int pdPrice, int pdPrice2, String pdCate, String pdBrand, String pdPhoto, String pdDes) {
        this.pd_id = pdId;
        this.pd_name = pdName;
        this.pd_price = pdPrice;
        this.pd_price2 = pdPrice2;
        this.pd_brand = pdBrand;
        this.pd_cate = pdCate;
        this.pd_des = pdDes;
        this.pd_photo = pdPhoto;
    }

    public String getPd_id() {
        return pd_id;
    }

    public void setPd_id(String pd_id) {
        this.pd_id = pd_id;
    }

    public String getPd_name() {
        return pd_name;
    }

    public void setPd_name(String pd_name) {
        this.pd_name = pd_name;
    }

    public int getPd_price() {
        return pd_price;
    }

    public void setPd_price(int pd_price) {
        this.pd_price = pd_price;
    }

    public int getPd_price2() {
        return pd_price2;
    }

    public void setPd_price2(int pd_price2) {
        this.pd_price2 = pd_price2;
    }

    public String getPd_brand() {
        return pd_brand;
    }

    public void setPd_brand(String pd_brand) {
        this.pd_brand = pd_brand;
    }

    public String getPd_cate() {
        return pd_cate;
    }

    public void setPd_cate(String pd_cate) {
        this.pd_cate = pd_cate;
    }

    public String getPd_photo() {
        return pd_photo;
    }

    public void setPd_photo(String pd_photo) {
        this.pd_photo = pd_photo;
    }

    public String getPd_des() {
        return pd_des;
    }

    public void setPd_des(String pd_des) {
        this.pd_des = pd_des;
    }

    public String getPd_skintype() {
        return pd_des;
    }

    public void setPd_skintype(String pd_des) {
        this.pd_des = pd_des;
    }
}