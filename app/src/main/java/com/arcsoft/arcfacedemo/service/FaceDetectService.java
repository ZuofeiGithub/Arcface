package com.arcsoft.arcfacedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.arcsoft.arcfacedemo.model.CameraInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

public class FaceDetectService extends Service {
    private Timer timer = null;
    private TimerTask task = null;
    private CameraInfo data;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getStringExtra("data");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startTimer();

    }

    @Override
    public void onDestroy() {
        stopTimer();
        super.onDestroy();
    }



    //服务中的进程
    public void startTimer(){
        final List<FaceInfo> faceInfoList = new ArrayList<>();
        if(timer == null){
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                   // int code = FaceDetectService.this.data.getFaceEngine().detectFaces(FaceDetectService.this.data.getNv21(), FaceDetectService.this.data.getWidth(), FaceDetectService.this.data.getHeight(), FaceEngine.CP_PAF_NV21, faceInfoList);
                    //LogUtils.i(faceInfoList.size());
                }
            };
            timer.schedule(task,1000,1000);
        }
    }
    public void stopTimer(){
        if(timer != null){
            task.cancel();
            timer.cancel();
            task = null;
            timer = null;
        }
    }

    public class Binder extends android.os.Binder{
        public void setData(CameraInfo cameraInfo){
            FaceDetectService.this.data = cameraInfo;
        }
    }
}
