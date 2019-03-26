package com.arcsoft.arcfacedemo.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arcsoft.arcfacedemo.R;
import com.arcsoft.arcfacedemo.net.FaceApi;
import com.arcsoft.arcfacedemo.net.IBannerCallBack;
import com.arcsoft.arcfacedemo.net.ITokenCallBack;
import com.arcsoft.arcfacedemo.util.ACache;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import me.yokeyword.fragmentation.SupportFragment;

import static android.content.Context.ALARM_SERVICE;

/**
 * 播放广告的
 */
public class AdvFragment extends SupportFragment implements OnBannerListener{


    public Context context;
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private ACache mCache;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adv_fragment,container,false);
        mCache = ACache.get(this.getContext());
        switchContent();
        initBanner(view);
        return view;
    }

    private void switchContent() {
        alarmManager = (AlarmManager)this.getContext().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction("action.REFRESHTEXTVIEW");
        pendingIntent = PendingIntent.getBroadcast(this.getContext(),
                100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                5000, 60000, pendingIntent);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.REFRESHTEXTVIEW");
        BroadcastReceiver receiver = new AlarmBroadcastReceiver();
        this.getContext().registerReceiver(receiver,intentFilter);
    }

    private class AlarmBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
//            alarmManager.cancel(pendingIntent);
            updateBanner();
        }
    }

    private void initBanner(View view) {
        banner = view.findViewById(R.id.banner);
        updateBanner();
    }

    private void updateBanner() {
        FaceApi.getInstance().getToken("zuofei", "123456", new ITokenCallBack() {
            @Override
            public void accessToken(String token) {

                FaceApi.getInstance().getBanners(token, new IBannerCallBack() {
                    @Override
                    public void banner(List<com.arcsoft.arcfacedemo.model.Banner> bannerList) {
                        if(bannerList.size() > 0){
                            //放图片地址的集合
                            list_path = new ArrayList<>();
                            //放标题的集合
                            list_title = new ArrayList<>();
                            for(int i = 0;i < bannerList.size();i++){
                                list_path.add(bannerList.get(i).getPicurl());
                                mCache.put("banner"+i,bannerList.get(i).getPicurl());
                                list_title.add(bannerList.get(i).getName());
                                mCache.put("bannername"+i,bannerList.get(i).getName());
                            }

                        }else{
                            for(int i = 0; i < 3;i++) {
                                list_path.add(mCache.getAsString("banner" + i));
                                list_title.add(mCache.getAsString("bannername" +i));
                            }
                        }
                        //设置内置样式，共有六种可以点入方法内逐一体验使用。
                        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
                        //设置图片加载器，图片加载器在下方
                        banner.setImageLoader(new GlideImageLoader());
                        //设置图片网址或地址的集合
                        banner.setImages(list_path);
                        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
                        banner.setBannerAnimation(Transformer.Default);
                        //设置轮播图的标题集合
                        banner.setBannerTitles(list_title);
                        //设置轮播间隔时间
                        banner.setDelayTime(3000);
                        //设置是否为自动轮播，默认是“是”。
                        banner.isAutoPlay(true);
                        //设置指示器的位置，小点点，左中右。
                        banner.setIndicatorGravity(BannerConfig.CENTER)
                                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                                .setOnBannerListener(AdvFragment.this)
                                //必须最后调用的方法，启动轮播图。
                                .start();
                    }
                });

            }
        });
    }

    @Override
    public void OnBannerClick(int position) {

    }
}
