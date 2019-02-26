package com.arcsoft.arcfacedemo.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.model.MessageInfo;
import com.blankj.utilcode.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

public class FragmentResultBkFragment extends SupportFragment {
    private MessageInfo mMsgInfo;
    public FragmentResultBkFragment(){
        EventBus.getDefault().register(this);
    }
    private CircleImageView circleImage;
    private TextView ageView;
    private TextView show_lablel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initData();
        return inflater.inflate(R.layout.fragment_result_bk, null);
    }

    private void initData() {
        mMsgInfo = new MessageInfo();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        circleImage = view.findViewById(R.id.circle_image);
        circleImage.setImageBitmap(mMsgInfo.getFaceMap());
        ageView = view.findViewById(R.id.age_view);
        show_lablel = view.findViewById(R.id.show_lablel);
        ageView.setText(mMsgInfo.getAge()+"岁");
        show_lablel.setText("测试测试");
        super.onViewCreated(view, savedInstanceState);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgEvent(MessageInfo msgInfo){
        LogUtils.i(msgInfo.getAge(),msgInfo.getFaceMap(),msgInfo.getGender());
       mMsgInfo = msgInfo;
    }
}
