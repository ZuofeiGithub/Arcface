package com.arcsoft.arcfacedemo.model;

import com.arcsoft.face.FaceEngine;

public class CameraInfo {

    FaceEngine faceEngine;
    byte[] nv21;
    int width;
    int height;


    public FaceEngine getFaceEngine() {
        return faceEngine;
    }

    public void setFaceEngine(FaceEngine faceEngine) {
        this.faceEngine = faceEngine;
    }
    public byte[] getNv21() {
        return nv21;
    }

    public void setNv21(byte[] nv21) {
        this.nv21 = nv21;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
