package com.example.models;

import java.io.Serializable;

public class Question implements Serializable {
    String quesNumber;
    String quesContent;
    String quesA;
    String quesB;
    String quesC;
    String quesD;
    boolean isCheckedA;
    boolean isCheckedB;
    boolean isCheckedC;
    boolean isCheckedD;
    boolean setCheckedA;
    boolean setCheckedB;
    boolean setCheckedC;
    boolean setCheckedD;


    public Question(boolean isCheckedA, boolean isCheckedB, boolean isCheckedC, boolean isCheckedD, boolean setCheckedA, boolean setCheckedB, boolean setCheckedC, boolean setCheckedD) {
        this.isCheckedA = isCheckedA;
        this.isCheckedB = isCheckedB;
        this.isCheckedC = isCheckedC;
        this.isCheckedD = isCheckedD;
        this.setCheckedA = setCheckedA;
        this.setCheckedB = setCheckedB;
        this.setCheckedC = setCheckedC;
        this.setCheckedD = setCheckedD;
    }

    public Question(String quesNumber, String quesContent, String quesA, String quesB, String quesC, String quesD) {
        this.quesNumber = quesNumber;
        this.quesContent = quesContent;
        this.quesA = quesA;
        this.quesB = quesB;
        this.quesC = quesC;
        this.quesD = quesD;

    }

    public boolean isCheckedA() {
        return isCheckedA;
    }

    public void setCheckedA(boolean checkedA) {
        isCheckedA = checkedA;
    }

    public boolean isCheckedB() {
        return isCheckedB;
    }

    public void setCheckedB(boolean checkedB) {
        isCheckedB = checkedB;
    }

    public boolean isCheckedC() {
        return isCheckedC;
    }

    public void setCheckedC(boolean checkedC) {
        isCheckedC = checkedC;
    }

    public boolean isCheckedD() {
        return isCheckedD;
    }

    public void setCheckedD(boolean checkedD) {
        isCheckedD = checkedD;
    }

    public boolean isSetCheckedA() {
        return setCheckedA;
    }

    public void setSetCheckedA(boolean setCheckedA) {
        this.setCheckedA = setCheckedA;
    }

    public boolean isSetCheckedB() {
        return setCheckedB;
    }

    public void setSetCheckedB(boolean setCheckedB) {
        this.setCheckedB = setCheckedB;
    }

    public boolean isSetCheckedC() {
        return setCheckedC;
    }

    public void setSetCheckedC(boolean setCheckedC) {
        this.setCheckedC = setCheckedC;
    }

    public boolean isSetCheckedD() {
        return setCheckedD;
    }

    public void setSetCheckedD(boolean setCheckedD) {
        this.setCheckedD = setCheckedD;
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
