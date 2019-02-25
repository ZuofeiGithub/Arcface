package com.arcsoft.arcfacedemo;

import android.graphics.Bitmap;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.ist.lifecyclerlib.ZLifeCycle;
import com.ist.lifecyclerlib.listener.LifeListenerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FragmentResultBkFragment extends SupportFragment {
    private Bitmap mface;
    public FragmentResultBkFragment(){
        EventBus.getDefault().register(this);

    }

    private CircleImageView circleImage;
    private TextView ageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtils.i("====================================="+FragmentResultBkFragment.class.toString()+"被创建");
        return inflater.inflate(R.layout.fragment_result_bk, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        circleImage = (CircleImageView) view.findViewById(R.id.circle_image);
        //circleImage.setBackgroundResource(R.drawable.circle);
        circleImage.setImageBitmap(mface);
        ageView = (TextView) view.findViewById(R.id.age_view);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onMyEvent(Bitmap face){
        LogUtils.i(face);
        mface = face;
    }

}
