package com.example.models;

import java.io.Serializable;
import java.sql.Blob;

public class Feedback implements Serializable {
    String FeedbackId;

    Blob UserThumb;
    String UserName;
    String Date;
    int Ratings;
    String FeedbackTitle;
    String FeedbackContent;

    public Feedback(String feedbackId, Blob userThumb, String userName, String date, int ratings, String feedbackTitle, String feedbackContent) {
        FeedbackId = feedbackId;
        UserThumb = userThumb;
        UserName = userName;
        Date = date;
        Ratings = ratings;
        FeedbackTitle = feedbackTitle;
        FeedbackContent = feedbackContent;
    }

    public String getFeedbackId() {
        return FeedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        FeedbackId = feedbackId;
    }

    public Blob getUserThumb() {
        return UserThumb;
    }

    public void setUserThumb(Blob userThumb) {
        UserThumb = userThumb;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getRatings() {
        return Ratings;
    }

    public void setRatings(int ratings) {
        Ratings = ratings;
    }

    public String getFeedbackTitle() {
        return FeedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        FeedbackTitle = feedbackTitle;
    }

    public String getFeedbackContent() {
        return FeedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        FeedbackContent = feedbackContent;
    }
}