package com.example.models;

import java.io.Serializable;

public class Question implements Serializable {
    String quesNumber;
    String quesContent;
    String quesA;
    String quesB;
    String quesC;
    String quesD;

    public Question(String quesNumber, String quesContent, String quesA, String quesB, String quesC, String quesD) {
        this.quesNumber = quesNumber;
        this.quesContent = quesContent;
        this.quesA = quesA;
        this.quesB = quesB;
        this.quesC = quesC;
        this.quesD = quesD;
    }

    public String getQuesNumber() {
        return quesNumber;
    }

    public void setQuesNumber(String quesNumber) {
        this.quesNumber = quesNumber;
    }

    public String getQuesContent() {
        return quesContent;
    }

    public void setQuesContent(String quesContent) {
        this.quesContent = quesContent;
    }

    public String getQuesA() {
        return quesA;
    }

    public void setQuesA(String quesA) {
        this.quesA = quesA;
    }

    public String getQuesB() {
        return quesB;
    }

    public void setQuesB(String quesB) {
        this.quesB = quesB;
    }

    public String getQuesC() {
        return quesC;
    }

    public void setQuesC(String quesC) {
        this.quesC = quesC;
    }

    public String getQuesD() {
        return quesD;
    }

    public void setQuesD(String quesD) {
        this.quesD = quesD;
    }
}
