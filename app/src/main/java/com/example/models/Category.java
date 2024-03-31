package com.example.models;

public class Category {
    String selectedTag;

    public Category(String selectedTag) {
        this.selectedTag = selectedTag;
    }

    public String getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(String selectedTag) {
        this.selectedTag = selectedTag;
    }
}
