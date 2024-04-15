package com.example.models;

import java.io.Serializable;

public class Feedback implements Serializable {

    int UserThumb;
    String UserName;
    String Date;
    double Ratings;
    String FeedbackTitle;
    String FeedbackContent;

    public Feedback(int userThumb, String userName, String date, double ratings, String feedbackTitle, String feedbackContent) {
        UserThumb = userThumb;
        UserName = userName;
        Date = date;
        Ratings = ratings;
        FeedbackTitle = feedbackTitle;
        FeedbackContent = feedbackContent;
    }

    public int getUserThumb() {
        return UserThumb;
    }

    public void setUserThumb(int userThumb) {
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

    public double getRatings() {
        return Ratings;
    }

    public void setRatings(double ratings) {
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