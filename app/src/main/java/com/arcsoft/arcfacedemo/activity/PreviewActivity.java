package com.arcsoft.arcfacedemo.activity;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.common.Constants;
import com.arcsoft.arcfacedemo.fragment.AdvFragment;
import com.arcsoft.arcfacedemo.fragment.BkFragment;
import com.arcsoft.arcfacedemo.fragment.FragmentResultBkFragment;
import com.arcsoft.arcfacedemo.model.CameraInfo;
import com.arcsoft.arcfacedemo.model.DrawInfo;
import com.arcsoft.arcfacedemo.model.MessageInfo;
import com.arcsoft.arcfacedemo.service.FaceDetectService;
import com.arcsoft.arcfacedemo.util.ConfigUtil;
import com.arcsoft.arcfacedemo.util.DrawHelper;
import com.arcsoft.arcfacedemo.util.NV21ToBitmap;
import com.arcsoft.arcfacedemo.util.camera.CameraHelper;
import com.arcsoft.arcfacedemo.util.camera.CameraListener;
import com.arcsoft.arcfacedemo.widget.FaceRectView;
import com.arcsoft.face.AgeInfo;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.Face3DAngle;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.GenderInfo;
import com.arcsoft.face.LivenessInfo;
import com.arcsoft.face.VersionInfo;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import dev.xesam.android.toolbox.timer.CountDownTimer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportActivity;

public class PreviewActivity extends SupportActivity implements ViewTreeObserver.OnGlobalLayoutListener, OnBannerListener, ServiceConnection {
    private static final String[] NEEDED_PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE};
    private int processMask = FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;
    private Integer cameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private FragmentResultBkFragment fragmentResultBkFragment;
    private FaceRectView faceRectView;
    private CameraHelper cameraHelper;
    private Camera.Size previewSize;
    private int mdisplayOrientation;

    private AdvFragment advFragment;
    //摄像机背景
    private BkFragment bkFragment;
    private DrawHelper drawHelper;
    private FaceEngine faceEngine;

    private boolean misMirror;
    private Bitmap finalFace;
    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;
    private int afCode = -1;
    private int mcameraId;

    private boolean status = true;
    private boolean isDetect = true;
    private boolean noface = true;

    private FaceDetectService.Binder faceBinder;

    private CameraInfo cameraInfo;

    CountDownTimer timer;
    CountDownTimer operatimer;

    List<AgeInfo> ageInfoList;
    List<GenderInfo> genderInfoList;
    List<Face3DAngle> face3DAngleList;
    List<LivenessInfo> faceLivenessInfoList;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        CrashReport.initCrashReport(getApplicationContext());
        initView();
        //Intent serviceIntent  = new Intent(PreviewActivity.this, FaceDetectService.class);
        //serviceIntent.putExtra("key",111);
//        startService(serviceIntent);
        cameraInfo = new CameraInfo();
        startTimer();
        // bindService(serviceIntent,this,BIND_AUTO_CREATE);
    }


    private void startTimer() {
        alarmManager = (AlarmManager)this.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction("action.PREVIEW");
        pendingIntent = PendingIntent.getBroadcast(this,
                100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                5000, 60000, pendingIntent);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.PREVIEW");
        BroadcastReceiver receiver = new AlarmBroadcastReceiver();
        registerReceiver(receiver,intentFilter);
    }

    private class AlarmBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
//            alarmManager.cancel(pendingIntent);
            LogUtils.i("Pre定时器");
        }
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().setAttributes(attributes);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        fragmentResultBkFragment = new FragmentResultBkFragment();
        LogUtils.getConfig().setGlobalTag("zuofei");
        previewView = findViewById(R.id.texture_preview);
        faceRectView = findViewById(R.id.face_rect_view);
        advFragment = new AdvFragment();
        bkFragment = new BkFragment();
        //在布局结束后才做初始化操作
        lockOrientation();
        previewView.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 在{@link #previewView}第一次布局完成后，去除该监听，并且进行引擎和相机的初始化
     */
    @Override
    public void onGlobalLayout() {
        previewView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
        } else {
            initEngine(); //初始化人脸识别引擎
            initCamera(); //初始化相机
            //初始化广告页
            loadRootFragment(R.id.fragmentGroup, advFragment);
        }
    }

    /**
     * Activity启动后就锁定为启动时的方向
     */
    private void lockOrientation() {
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
            default:
                break;
        }
    }

    /**
     * 激活引擎
     *
     * @param view
     */
    public void activeEngine(final View view) {
        if (!checkPermissions(NEEDED_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, ACTION_REQUEST_PERMISSIONS);
            return;
        }
        if (view != null) {
            view.setClickable(false);
        }
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                FaceEngine faceEngine = new FaceEngine();
                int activeCode = faceEngine.active(PreviewActivity.this, Constants.APP_ID, Constants.SDK_KEY);
                emitter.onNext(activeCode);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer activeCode) {
                        if (activeCode == ErrorInfo.MOK) {
                            ToastUtils.showShort("激活成功");
                            initEngine();
                            initCamera();
                            if (cameraHelper != null) {
                                cameraHelper.start();
                            }
                        } else if (activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
                            ToastUtils.showShort("已经激活");
                        } else {
                            ToastUtils.showShort("激活失败,失败码:%d", activeCode);
                        }

                        if (view != null) {
                            view.setClickable(true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    protected void onDestroy() {
        if (cameraHelper != null) {
            cameraHelper.release();
            cameraHelper = null;
        }
        unbindService(this);
        unInitEngine();
        super.onDestroy();
    }

    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this.getApplicationContext(), neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    private void initCamera() {
        //播放音效
        //MusicManager.getInstance().play(PreviewActivity.this, R.raw.wayo);
        CameraListener cameraListener = new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                previewSize = camera.getParameters().getPreviewSize();
                mdisplayOrientation = displayOrientation;
                misMirror = isMirror;
                mcameraId = cameraId;
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation, cameraId, isMirror);
            }

            @Override
            public void onPreview(final byte[] nv21, Camera camera) {
                runDetect(nv21);
            }

            @Override
            public void onCameraClosed() {
            }

            @Override
            public void onCameraError(Exception e) {

            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
                if (drawHelper != null) {
                    drawHelper.setCameraDisplayOrientation(displayOrientation);
                }
            }
        };
        cameraHelper = new CameraHelper.Builder()
                .previewViewSize(new Point(previewView.getMeasuredWidth(), previewView.getMeasuredHeight()))
                .rotation(getWindowManager().getDefaultDisplay().getRotation())
                .specificCameraId(cameraID != null ? cameraID : Camera.CameraInfo.CAMERA_FACING_FRONT)
                .isMirror(false)
                .previewOn(previewView)
                .cameraListener(cameraListener)
                .build();
        cameraHelper.init();
    }

    /**
     * 进行人脸检测
     *
     * @param nv21
     */
    private void runDetect(final byte[] nv21) {
        if (faceRectView != null) {
            faceRectView.clearFaceInfo();
        }
        if (noface) {
            noface = false;
            if (timer == null) {
                timer = new CountDownTimer(30000, 1000) {
                    @Override
                    public void onFinish() {
                        replaceFragment(advFragment, true);
                        status = true;
                    }
                };
            }
            timer.start();
        }
        final List<FaceInfo> faceInfoList = new ArrayList<>();
        int code = faceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
        if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
            code = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
            if (code != ErrorInfo.MOK) {
                //FragmentUtils.replace(bkFragment, advFragment);
                LogUtils.i(status);

                return;
            }
            if (!noface)
                noface = true;
            LogUtils.i("检测到人脸");
            final Rect faceRect = adjustRect(faceInfoList.get(0).getRect(), previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), mdisplayOrientation, mcameraId, misMirror, false, false);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            int screenHeight = dm.heightPixels;
            int px = screenWidth / 2;
            int py = screenHeight / 2;
            if (faceRect.contains(px, py)) {
                if (status) {
                    status = false;
                    //LogUtils.i("检测到人脸开始操作");
                    faceOperation(nv21, faceRect);
                    faceInfoList.clear();
                }
            }

            drawFace(faceInfoList);
        } else {
            return;
        }
    }

    /**
     * 画人脸框
     *
     * @param faceInfoList
     */
    private void drawFace(List<FaceInfo> faceInfoList) {
        ageInfoList = new ArrayList<>();
        genderInfoList = new ArrayList<>();
        face3DAngleList = new ArrayList<>();
        faceLivenessInfoList = new ArrayList<>();

        int ageCode = faceEngine.getAge(ageInfoList);
        int genderCode = faceEngine.getGender(genderInfoList);
        int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);
        int livenessCode = faceEngine.getLiveness(faceLivenessInfoList);

        if ((ageCode | genderCode | face3DAngleCode | livenessCode) != ErrorInfo.MOK) {
            return;
        }
        if (faceRectView != null && drawHelper != null) {
            List<DrawInfo> drawInfoList = new ArrayList<>();
            for (int i = 0; i < faceInfoList.size(); i++) {
                drawInfoList.add(new DrawInfo(faceInfoList.get(i).getRect(), genderInfoList.get(i).getGender(), ageInfoList.get(i).getAge(), faceLivenessInfoList.get(i).getLiveness(), null));

            }
            drawHelper.draw(faceRectView, drawInfoList);
        }
    }

    private void faceOperation(final byte[] nv21, final Rect faceRect) {
        //获取真实人脸位置

        loadRootFragment(R.id.fragmentGroup, bkFragment);
        NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(PreviewActivity.this);
        Bitmap face = nv21ToBitmap.nv21ToBitmap(nv21, previewSize.width, previewSize.height);
        face = ImageUtils.rotate(face, 270, 0, 0);
        face = convert(face, face.getWidth(), face.getHeight());
        finalFace = face;
        if (operatimer == null) {
            operatimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    MessageInfo msgInfo = new MessageInfo();
                    msgInfo.setAge(ageInfoList.get(0).getAge());
                    msgInfo.setGender(genderInfoList.get(0).getGender());
                    msgInfo.setFaceMap(finalFace);
                    EventBus.getDefault().post(msgInfo);
                    try {
                        finalFace = ImageUtils.clip(finalFace, faceRect.left > 0 ? faceRect.left : 0, faceRect.top > 0 ? faceRect.top : 0, faceRect.width(), faceRect.height());
                        replaceFragment(fragmentResultBkFragment, true);
                        //FaceApi.getInstance().verifyFace(finalFace);
                    } catch (Exception e) {
                    }
                }
            };
        }
        operatimer.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ACTION_REQUEST_PERMISSIONS) {
            boolean isAllGranted = true;
            for (int grantResult : grantResults) {
                isAllGranted &= (grantResult == PackageManager.PERMISSION_GRANTED);
            }
            if (isAllGranted) {
                activeEngine(null);
            } else {
                Toast.makeText(this.getApplicationContext(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * 初始化引擎
     */
    private void initEngine() {
        faceEngine = new FaceEngine();
        afCode = faceEngine.init(this.getApplicationContext(), FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                16, 1, FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS | FaceEngine.ASF_FACE_RECOGNITION);
        VersionInfo versionInfo = new VersionInfo();
        faceEngine.getVersion(versionInfo);
        if (afCode != ErrorInfo.MOK) {
            ToastUtils.showShort("激活失败,失败码:%d", afCode);
        }
    }

    /**
     * 取消初始化
     */
    private void unInitEngine() {

        if (afCode == 0) {
            afCode = faceEngine.unInit();
        }
    }

    /**
     * @param ftRect                   FT人脸框
     * @param previewWidth             相机预览的宽度
     * @param previewHeight            相机预览高度
     * @param canvasWidth              画布的宽度
     * @param canvasHeight             画布的高度
     * @param cameraDisplayOrientation 相机预览方向
     * @param cameraId                 相机ID
     * @param isMirror                 是否水平镜像显示（若相机是镜像显示的，设为true，用于纠正）
     * @param mirrorHorizontal         为兼容部分设备使用，水平再次镜像
     * @param mirrorVertical           为兼容部分设备使用，垂直再次镜像
     * @return 调整后的需要被绘制到View上的rect
     */
    private Rect adjustRect(Rect ftRect, int previewWidth, int previewHeight, int canvasWidth, int canvasHeight, int cameraDisplayOrientation, int cameraId,
                            boolean isMirror, boolean mirrorHorizontal, boolean mirrorVertical) {

        if (ftRect == null) {
            return null;
        }
        Rect rect = new Rect(ftRect);

        float horizontalRatio;
        float verticalRatio;
        if (cameraDisplayOrientation % 180 == 0) {
            horizontalRatio = (float) canvasWidth / (float) previewWidth;
            verticalRatio = (float) canvasHeight / (float) previewHeight;
        } else {
            horizontalRatio = (float) canvasHeight / (float) previewWidth;
            verticalRatio = (float) canvasWidth / (float) previewHeight;
        }
        rect.left *= horizontalRatio;
        rect.right *= horizontalRatio;
        rect.top *= verticalRatio;
        rect.bottom *= verticalRatio;
        Rect newRect = new Rect();
        switch (cameraDisplayOrientation) {
            case 0:
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    newRect.left = canvasWidth - rect.right;
                    newRect.right = canvasWidth - rect.left;
                } else {
                    newRect.left = rect.left;
                    newRect.right = rect.right;
                }
                newRect.top = rect.top;
                newRect.bottom = rect.bottom;
                break;
            case 90:
                newRect.right = canvasWidth - rect.top;
                newRect.left = canvasWidth - rect.bottom;
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    newRect.top = canvasHeight - rect.right;
                    newRect.bottom = canvasHeight - rect.left;
                } else {
                    newRect.top = rect.left;
                    newRect.bottom = rect.right;
                }
                break;
            case 180:
                newRect.top = canvasHeight - rect.bottom;
                newRect.bottom = canvasHeight - rect.top;
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    newRect.left = rect.left;
                    newRect.right = rect.right;
                } else {
                    newRect.left = canvasWidth - rect.right;
                    newRect.right = canvasWidth - rect.left;
                }
                break;
            case 270:
                newRect.left = rect.top;
                newRect.right = rect.bottom;
                if (cameraId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    newRect.top = rect.left;
                    newRect.bottom = rect.right;
                } else {
                    newRect.top = canvasHeight - rect.right;
                    newRect.bottom = canvasHeight - rect.left;
                }
                break;
            default:
                break;
        }

        /**
         * isMirror mirrorHorizontal finalIsMirrorHorizontal
         * true         true                false
         * false        false               false
         * true         false               true
         * false        true                true
         *
         * XOR
         */
        if (isMirror ^ mirrorHorizontal) {
            int left = newRect.left;
            int right = newRect.right;
            newRect.left = canvasWidth - right;
            newRect.right = canvasWidth - left;
        }
        if (mirrorVertical) {
            int top = newRect.top;
            int bottom = newRect.bottom;
            newRect.top = canvasHeight - bottom;
            newRect.bottom = canvasHeight - top;
        }
        return newRect;
    }

    @Override
    public void OnBannerClick(int position) {

    }

    Bitmap convert(Bitmap a, int width, int height) {
        int w = a.getWidth();
        int h = a.getHeight();
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        Matrix m = new Matrix();
        m.postScale(-1, 1);   //镜像水平翻转
        Bitmap new2 = Bitmap.createBitmap(a, 0, 0, w, h, m, true);
        cv.drawBitmap(new2, new Rect(0, 0, new2.getWidth(), new2.getHeight()), new Rect(0, 0, width, height), null);
        return newb;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        faceBinder = (FaceDetectService.Binder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
