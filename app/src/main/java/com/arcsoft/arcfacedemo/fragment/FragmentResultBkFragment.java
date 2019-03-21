package com.arcsoft.arcfacedemo.fragment;

import android.content.Context;
import android.graphics.Color;
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

    public FragmentResultBkFragment() {
        mMsgInfo = new MessageInfo();
        EventBus.getDefault().register(this);
    }

    private CircleImageView circleImage;
    private TextView ageView;
    private TextView show_lablel;
    private FrameLayout resultLayout;
    private TextView descript_bk;
    private List<TextView> textViewList;
    private List<String> maleStrList;
    private List<String> femaleStrList;
    private ChooseStyleViewFragment chooseStyleViewFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_result_bk, null);
        circleImage = view.findViewById(R.id.circle_image);
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
        chooseStyleViewFragment = new ChooseStyleViewFragment();
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

        ageView.setText(String.valueOf(mMsgInfo.getAge()) + "岁");

        Random random = new Random();
        int num = random.nextInt(6) % (6 - 1 + 1) + 1;


        if (mMsgInfo.getGender() == GenderInfo.MALE) {
            ageView.setTextColor(Color.BLUE);
            Drawable drawable = getResources().getDrawable(R.drawable.boy);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            descript_bk.setCompoundDrawables(null, null, drawable, null);
            descript_bk.setText("帅气小伙");
//            for (int i = 0; i < num; i++) {
//                textViewList.get(i).setVisibility(View.VISIBLE);
//                textViewList.get(i).setText(maleStrList.get(i));
//            }
            circleImage.setImageBitmap(mMsgInfo.getFaceMap());
        } else if (mMsgInfo.getGender() == GenderInfo.FEMALE) {
            ageView.setTextColor(Color.RED);
            Drawable drawable = getResources().getDrawable(R.drawable.girl);
            // / 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            descript_bk.setCompoundDrawables(null, null, drawable, null);
            descript_bk.setText("可爱女生");
            circleImage.setImageBitmap(mMsgInfo.getFaceMap());
//            for (int i = 0; i < num; i++) {
//                textViewList.get(i).setVisibility(View.VISIBLE);
//                textViewList.get(i).setText(femaleStrList.get(i));
//            }
        }
        show_lablel.setText("不要走开，还有专属你的惊喜");
        super.onViewCreated(view, savedInstanceState);

        CountDownTimer timer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post(mMsgInfo);
                loadRootFragment(R.id.resultfraglayout,new ChooseStyleViewFragment());
                resultLayout.setVisibility(View.VISIBLE);
            }
        };
        timer.start();

        loadRootFragment(R.id.label_fraglayout,new LabelFragmentFragment());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void msgEvent(MessageInfo msgInfo) {
        mMsgInfo = msgInfo;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
