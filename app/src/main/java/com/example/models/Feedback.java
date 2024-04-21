package com.example.models;

import java.io.Serializable;
import java.sql.Blob;

public class Feedback implements Serializable {
    private String feedbackId;
    private String userThumbUrl; // Đường dẫn ảnh đại diện của người dùng
    private String userName;
    private String date;
    private int ratings;
    private String feedbackTitle;
    private String feedbackContent;

    public Feedback(String feedbackId, String userThumbUrl, String userName, String date, int ratings, String feedbackTitle, String feedbackContent) {
        this.feedbackId = feedbackId;
        this.userThumbUrl = userThumbUrl;
        this.userName = userName;
        this.date = date;
        this.ratings = ratings;
        this.feedbackTitle = feedbackTitle;
        this.feedbackContent = feedbackContent;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getUserThumbUrl() {
        return userThumbUrl;
    }

    public void setUserThumbUrl(String userThumbUrl) {
        this.userThumbUrl = userThumbUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }
}