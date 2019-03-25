package com.arcsoft.arcfacedemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.model.MessageInfo;
import com.arcsoft.face.GenderInfo;
import com.blankj.utilcode.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.yokeyword.fragmentation.SupportFragment;

public class LabelFragmentFragment extends SupportFragment {

    private TextView lablelOne;
    private TextView labelTwo;
    private TextView labelThree;
    private TextView labelFour;
    private TextView labelFive;
    private TextView labelSix;
    private List<TextView> textViewList;
    private List<String> maleStrList;
    private List<String> femaleStrList;
    private MessageInfo mMsgInfo;

    public LabelFragmentFragment() {
        mMsgInfo = new MessageInfo();
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.label_fragment, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lablelOne = view.findViewById(R.id.lablel_one);
        labelTwo = view.findViewById(R.id.label_two);
        labelThree =  view.findViewById(R.id.label_three);
        labelFour = view.findViewById(R.id.label_four);
        labelFive =  view.findViewById(R.id.label_five);
        labelSix =  view.findViewById(R.id.label_six);
        maleStrList = new ArrayList<>();
        femaleStrList = new ArrayList<>();
        maleStrList.add("温文尔雅");
        maleStrList.add("一表人才");
        maleStrList.add("气宇不凡");
        maleStrList.add("玉树临风");
        maleStrList.add("品貌非凡");
        maleStrList.add("风流倜傥");
        femaleStrList.add("闭月绣花");
        femaleStrList.add("卓越多姿");
        femaleStrList.add("窈窕淑女");
        femaleStrList.add("亭亭玉立");
        femaleStrList.add("婀娜多姿");
        femaleStrList.add("妍姿艳质");
        //清新俊逸、仪表堂堂、仪表不凡、英俊潇洒、高大威猛
        textViewList = new ArrayList<>();
        textViewList.add(lablelOne);
        textViewList.add(labelTwo);
        textViewList.add(labelThree);
        textViewList.add(labelFour);
        textViewList.add(labelFive);
        textViewList.add(labelSix);

        Random random = new Random();
        int num = random.nextInt(6) % (6 - 1 + 1) + 1;
        if(mMsgInfo.getGender() == GenderInfo.MALE) {
            for (int i = 0; i < num; i++) {
                textViewList.get(i).setVisibility(View.VISIBLE);
                textViewList.get(i).setText(maleStrList.get(i));
            }
        }else if(mMsgInfo.getGender() == GenderInfo.FEMALE){
            for (int i = 0; i < num; i++) {
                textViewList.get(i).setVisibility(View.VISIBLE);
                textViewList.get(i).setText(femaleStrList.get(i));
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgEvent(MessageInfo msgInfo) {
        mMsgInfo = msgInfo;
    }

}
