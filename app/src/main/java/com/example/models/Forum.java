package com.example.models;

import java.io.Serializable;

public class Forum implements Serializable {
    String fr_id;
    int fr_avatar;
    String fr_username;
    String fr_date;
    double fr_rating;
    String fr_reviewtitle;
    String fr_reviewcontent;

    //Constructor


    public Forum(String fr_id, int fr_avatar, String fr_username, String fr_date, double fr_rating, String fr_reviewtitle, String fr_reviewcontent) {
        this.fr_id = fr_id;
        this.fr_avatar = fr_avatar;
        this.fr_username = fr_username;
        this.fr_date = fr_date;
        this.fr_rating = fr_rating;
        this.fr_reviewtitle = fr_reviewtitle;
        this.fr_reviewcontent = fr_reviewcontent;
    }

    //Getter and setter

    public String getFr_id() {
        return fr_id;
    }

    public void setFr_id(String fr_id) {
        this.fr_id = fr_id;
    }

    public int getFr_avatar() {
        return fr_avatar;
    }

    public void setFr_avatar(int fr_avatar) {
        this.fr_avatar = fr_avatar;
    }

    public String getFr_username() {
        return fr_username;
    }

    public void setFr_username(String fr_username) {
        this.fr_username = fr_username;
    }

    public String getFr_date() {
        return fr_date;
    }

    public void setFr_date(String fr_date) {
        this.fr_date = fr_date;
    }

    public double getFr_rating() {
        return fr_rating;
    }

    public void setFr_rating(double fr_rating) {
        this.fr_rating = fr_rating;
    }

    public String getFr_reviewtitle() {
        return fr_reviewtitle;
    }

    public void setFr_reviewtitle(String fr_reviewtitle) {
        this.fr_reviewtitle = fr_reviewtitle;
    }

    public String getFr_reviewcontent() {
        return fr_reviewcontent;
    }

    public void setFr_reviewcontent(String fr_reviewcontent) {
        this.fr_reviewcontent = fr_reviewcontent;
    }
}
