package com.arcsoft.arcfacedemo.activity;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.common.Constants;
import com.arcsoft.arcfacedemo.fragment.AdvFragment;
import com.arcsoft.arcfacedemo.fragment.BkFragment;
import com.arcsoft.arcfacedemo.fragment.FragmentResultBkFragment;
import com.arcsoft.arcfacedemo.model.DrawInfo;
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
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.yokeyword.fragmentation.SupportActivity;

public class PreviewActivity extends SupportActivity implements ViewTreeObserver.OnGlobalLayoutListener, OnBannerListener {

    private int afCode = -1;
    private int processMask = FaceEngine.ASF_AGE | FaceEngine.ASF_FACE3DANGLE | FaceEngine.ASF_GENDER | FaceEngine.ASF_LIVENESS;
    //显示方向
    private int mdisplayOrientation;
    private int mcameraId;
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private boolean noface = true;
    private boolean status = true;
    private boolean misMirror;
    private CameraHelper cameraHelper;
    private DrawHelper drawHelper;
    private Camera.Size previewSize;
    private Integer cameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private FaceEngine faceEngine;
    /**
     * 相机预览显示的控件，可为SurfaceView或TextureView
     */
    private View previewView;
    private FaceRectView faceRectView;
    //广告页
    private AdvFragment advFragment;
    //摄像机背景
    private BkFragment bkFragment;
    //获取头像后弹出的页面
    private FragmentResultBkFragment fragmentResultBkFragment;
    /**
     * 所需的所有权限信息
     */
    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        initView();
    }

    private void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            getWindow().setAttributes(attributes);
        }
        lockOrientation();

        LogUtils.getConfig().setGlobalTag("zuofei");
        previewView = findViewById(R.id.texture_preview);
        faceRectView = findViewById(R.id.face_rect_view);
        advFragment = new AdvFragment();
        bkFragment = new BkFragment();
        fragmentResultBkFragment = new FragmentResultBkFragment();
        loadRootFragment(R.id.fragmentGroup, advFragment);
        //在布局结束后才做初始化操作
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
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
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
                if (faceRectView != null) {
                    faceRectView.clearFaceInfo();
                }
                final List<FaceInfo> faceInfoList = new ArrayList<>();
                int code = faceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
                if (code == ErrorInfo.MOK && faceInfoList.size() > 0) {
                    code = faceEngine.process(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList, processMask);
                    if (code != ErrorInfo.MOK) {
                        if (noface) {
                            noface = false;
                            status = true;
                            replaceFragment(advFragment, true);
                        }
                        return;
                    }

                    //获取真实人脸位置
                    final Rect faceRect = adjustRect(faceInfoList.get(0).getRect(), previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), mdisplayOrientation, mcameraId, misMirror, false, false);

                    if (faceRect.contains(300, 800)) {
                        if (status) {
                            status = false;
                            noface = true;
                            loadRootFragment(R.id.fragmentGroup,bkFragment);
                            CountDownTimer timer = new CountDownTimer(2000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }
                                @Override
                                public void onFinish() {
                                    NV21ToBitmap nv21ToBitmap = new NV21ToBitmap(PreviewActivity.this);
                                    Bitmap face = nv21ToBitmap.nv21ToBitmap(nv21, previewSize.width, previewSize.height);
                                    face = ImageUtils.rotate(face, 0, 0, 0);
                                    final Bitmap finalFace = face;
                                    //Bitmap clipFace = ImageUtils.clip(finalFace, faceRect.left, faceRect.top, faceRect.width(), faceRect.height());
                                    replaceFragment(fragmentResultBkFragment, true);
                                    EventBus.getDefault().post(finalFace);

                                    //FaceApi.getInstance().verifyFace(clipFace);
                                }
                            };
                            timer.start();
                        }
                    }
                } else {
                    return;
                }


                List<AgeInfo> ageInfoList = new ArrayList<>();
                List<GenderInfo> genderInfoList = new ArrayList<>();
                List<Face3DAngle> face3DAngleList = new ArrayList<>();
                List<LivenessInfo> faceLivenessInfoList = new ArrayList<>();

                int ageCode = faceEngine.getAge(ageInfoList);
                int genderCode = faceEngine.getGender(genderInfoList);
                int face3DAngleCode = faceEngine.getFace3DAngle(face3DAngleList);
                int livenessCode = faceEngine.getLiveness(faceLivenessInfoList);

                //有其中一个的错误码不为0，return
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
}
