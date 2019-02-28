package com.arcsoft.arcfacedemo.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportFragment;

public class FragmentResultBkFragment extends SupportFragment {
    private MessageInfo mMsgInfo;
    public FragmentResultBkFragment(){
        mMsgInfo = new MessageInfo();
        EventBus.getDefault().register(this);
    }
    private CircleImageView circleImage;
    private TextView ageView;
    private TextView show_lablel;
    private FrameLayout resultLayout;
    private TextView descript_bk;
    private List<TextView> textViewList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_bk, null);
        circleImage = view.findViewById(R.id.circle_image);
        circleImage.setImageBitmap(mMsgInfo.getFaceMap());
        ageView = view.findViewById(R.id.age_view);
        descript_bk = view.findViewById(R.id.face_descript);
        show_lablel = view.findViewById(R.id.show_lablel);
        resultLayout = view.findViewById(R.id.resultfraglayout);
        TextView label_one = view.findViewById(R.id.lablel_one);
        TextView label_two = view.findViewById(R.id.label_two);
        TextView label_three = view.findViewById(R.id.label_three);
        TextView label_four = view.findViewById(R.id.label_four);
        TextView label_five = view.findViewById(R.id.label_five);
        TextView label_six = view.findViewById(R.id.label_six);
        textViewList = new ArrayList<>();
        textViewList.add(label_one);
        textViewList.add(label_two);
        textViewList.add(label_three);
        textViewList.add(label_four);
        textViewList.add(label_five);
        textViewList.add(label_six);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LogUtils.i("onViewCreated"+mMsgInfo.getAge());
        ageView.setText(String.valueOf(mMsgInfo.getAge())+"岁");

        Random random = new Random();
        int num = random.nextInt(6)%(6-1+1) + 1;
        LogUtils.i("随机数:"+num);
        for (int i = 0; i < num;i++){
            textViewList.get(i).setVisibility(View.VISIBLE);
        }

        if(mMsgInfo.getGender() == GenderInfo.MALE) {
            Drawable drawable = getResources().getDrawable(R.drawable.boy);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            descript_bk.setCompoundDrawables(null, null, drawable, null);
            descript_bk.setText("哇,帅气的小伙");
        }else if(mMsgInfo.getGender() == GenderInfo.FEMALE){
            Drawable drawable = getResources().getDrawable(R.drawable.girl);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            descript_bk.setCompoundDrawables(null, null, drawable, null);
            descript_bk.setText("漂亮的美女");
        }
        show_lablel.setText("不要走开，还有专属你的惊喜");
        CountDownTimer timer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                resultLayout.setVisibility(View.VISIBLE);
                loadRootFragment(R.id.resultfraglayout,new ChooseStyleViewFragment());
            }
        };
        timer.start();
        super.onViewCreated(view, savedInstanceState);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgEvent(MessageInfo msgInfo){
        LogUtils.i(msgInfo.getAge(),msgInfo.getFaceMap(),msgInfo.getGender());
       mMsgInfo = msgInfo;
    }
}
