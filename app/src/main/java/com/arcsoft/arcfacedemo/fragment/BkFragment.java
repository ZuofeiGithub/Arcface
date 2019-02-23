package com.arcsoft.arcfacedemo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arcsoft.arcfacedemo.R;
import com.blankj.utilcode.util.LogUtils;

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
