package com.arcsoft.arcfacedemo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arcsoft.arcfacedemo.R;
import com.blankj.utilcode.util.LogUtils;
import com.ist.lifecyclerlib.ZLifeCycle;
import com.ist.lifecyclerlib.listener.LifeListenerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;

public class BkFragment extends SupportFragment {
   public BkFragment(){
        EventBus.getDefault().register(this);
    }
    private static BkFragment instance = null;
    public static ISupportFragment newInstance(){
        if(instance == null){
            instance = new BkFragment();
        }
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZLifeCycle.with(this.getActivity(), new LifeListenerAdapter() {
            @Override
            public void onStart() {
                super.onStart();
                LogUtils.i(this.getClass().toString()+"开始");
            }

            @Override
            public void onResume() {
                super.onResume();
                LogUtils.i(this.getClass().toString()+"继续");
            }

            @Override
            public void onPause() {
                super.onPause();
                LogUtils.i(this.getClass().toString()+"暂停");
            }

            @Override
            public void onStop() {
                super.onStop();
                LogUtils.i(this.getClass().toString()+"停止");
            }

            @Override
            public void onDestroy() {
                super.onDestroy();
                LogUtils.i(this.getClass().toString()+"销毁");
            }

            @Override
            public void onFail(String errorMsg) {
                LogUtils.i(this.getClass().toString()+"错误");
                super.onFail(errorMsg);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bk_view,null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadRootFragment(R.id.bk_layout,new UserPhotoFragment());
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMyEvent(Bitmap face){

        EventBus.getDefault().post("message");
    }
}
