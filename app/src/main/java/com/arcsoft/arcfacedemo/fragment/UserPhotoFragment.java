package com.arcsoft.arcfacedemo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.util.WidgetController;
import com.blankj.utilcode.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

public class UserPhotoFragment extends SupportFragment {
    private CircleImageView profileImage;
    private Bitmap mface;

    public void subscriber(){
        //在构造方法中订阅消息
        EventBus.getDefault().register(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userphoto_view, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Picasso.get().load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1550721735506&di=b328ed9e3193b4c076d85aee6186298f&imgtype=0&src=http%3A%2F%2Fpic.qqtn.com%2Fup%2F2017-12%2F15124441076225752.jpg").into(profileImage);
    }

    @Subscribe //在ui线程执行
    public void onMyEvent(Bitmap face) {
        LogUtils.i("接受到消息");
        mface = face;
    }
}
