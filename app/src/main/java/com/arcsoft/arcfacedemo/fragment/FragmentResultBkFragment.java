package com.arcsoft.arcfacedemo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arcsoft.arcfacedemo.R;
import com.blankj.utilcode.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

public class FragmentResultBkFragment extends SupportFragment {
    private Bitmap mFace;
    public FragmentResultBkFragment(){
        EventBus.getDefault().register(this);
    }
    private CircleImageView circleImage;
    private TextView ageView;
    private TextView show_lablel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_result_bk, null);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circleImage =  view.findViewById(R.id.circle_image);
        circleImage.setImageBitmap(mFace);
        ageView = view.findViewById(R.id.age_view);
        show_lablel = view.findViewById(R.id.show_lablel);
        show_lablel.setText("测试测试");
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgEvent(Bitmap face){
        LogUtils.i(face);
        mFace = face;
    }
}
