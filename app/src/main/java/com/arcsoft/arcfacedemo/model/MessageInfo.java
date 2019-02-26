package com.arcsoft.arcfacedemo.model;

import android.graphics.Bitmap;

public class MessageInfo {
    private Bitmap faceMap;
    private int age;
    private int gender;

    public Bitmap getFaceMap() {
        return faceMap;
    }

    public void setFaceMap(Bitmap faceMap) {
        this.faceMap = faceMap;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
