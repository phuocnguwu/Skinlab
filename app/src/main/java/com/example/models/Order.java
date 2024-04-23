package com.example.models;


import java.io.Serializable;


public class Order implements Serializable {
    String user_id;
    String product_order1;
    int quantity_product1;
    String product_order2;
    int quantity_product2;
    String product_order3;
    int quantity_product3;
    String status;


    public Order(String user_id, String product_order1, int quantity_product1, String product_order2, int quantity_product2, String product_order3, int quantity_product3, String status) {
        this.user_id = user_id;
        this.product_order1 = product_order1;
        this.quantity_product1 = quantity_product1;
        this.product_order2 = product_order2;
        this.quantity_product2 = quantity_product2;
        this.product_order3 = product_order3;
        this.quantity_product3 = quantity_product3;
        this.status = status;
    }


    public String getUser_id() {
        return user_id;
    }


    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getProduct_order1() {
        return product_order1;
    }


    public void setProduct_order1(String product_order1) {
        this.product_order1 = product_order1;
    }


    public int getQuantity_product1() {
        return quantity_product1;
    }


    public void setQuantity_product1(int quantity_product1) {
        this.quantity_product1 = quantity_product1;
    }


    public String getProduct_order2() {
        return product_order2;
    }


    public void setProduct_order2(String product_order2) {
        this.product_order2 = product_order2;
    }


    public int getQuantity_product2() {
        return quantity_product2;
    }


    public void setQuantity_product2(int quantity_product2) {
        this.quantity_product2 = quantity_product2;
    }


    public String getProduct_order3() {
        return product_order3;
    }


    public void setProduct_order3(String product_order3) {
        this.product_order3 = product_order3;
    }


    public int getQuantity_product3() {
        return quantity_product3;
    }


    public void setQuantity_product3(int quantity_product3) {
        this.quantity_product3 = quantity_product3;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }
}
