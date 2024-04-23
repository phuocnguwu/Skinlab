package com.example.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Feedback implements Parcelable {
    private String feedbackId;
    private String userThumbUrl;
    private String userName;
    private String date;
    private int ratings;
    private String feedbackTitle;
    private String feedbackContent;

    // Constructor
    public Feedback(String feedbackId, String userThumbUrl, String userName, String date, int ratings, String feedbackTitle, String feedbackContent) {
        this.feedbackId = feedbackId;
        this.userThumbUrl = userThumbUrl;
        this.userName = userName;
        this.date = date;
        this.ratings = ratings;
        this.feedbackTitle = feedbackTitle;
        this.feedbackContent = feedbackContent;
    }

    // Parcelable implementation
    protected Feedback(Parcel in) {
        feedbackId = in.readString();
        userThumbUrl = in.readString();
        userName = in.readString();
        date = in.readString();
        ratings = in.readInt();
        feedbackTitle = in.readString();
        feedbackContent = in.readString();
    }

    public static final Creator<Feedback> CREATOR = new Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel in) {
            return new Feedback(in);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(feedbackId);
        dest.writeString(userThumbUrl);
        dest.writeString(userName);
        dest.writeString(date);
        dest.writeInt(ratings);
        dest.writeString(feedbackTitle);
        dest.writeString(feedbackContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters and setters
    public String getFeedbackId() {
        return feedbackId;
    }

    public String getUserThumbUrl() {
        return userThumbUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public int getRatings() {
        return ratings;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }
}
