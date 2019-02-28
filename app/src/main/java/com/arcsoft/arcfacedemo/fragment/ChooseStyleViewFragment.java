package com.arcsoft.arcfacedemo.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.arcsoft.arcfacedemo.R;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.mpt.android.stv.Slice;
import com.mpt.android.stv.SpannableTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

public class ChooseStyleViewFragment extends SupportFragment {

    private SpannableTextView titleChooose;
    private NineGridImageView nineGridImageView;
    private List<String> viewList;
    private ImageButton flushBtn;
    private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {

        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String photo) {
            Picasso.get()
                    .load(photo)
                    .into(imageView);
        }

        @Override
        protected ImageView generateImageView(Context context) {
            return super.generateImageView(context);
        }

        @Override
        protected void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
            super.onItemImageClick(context, imageView, index, list);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_style_view, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleChooose =  view.findViewById(R.id.title_chooose);
        titleChooose.addSlice(new Slice.Builder("猜你喜欢")
                .textColor(Color.parseColor("#f6990e"))
                .textSize(80)
                .style(Typeface.BOLD | Typeface.ITALIC)
                .build());
        titleChooose.addSlice(new Slice.Builder("\u3000\u3000\u3000\u3000").build());
        titleChooose.addSlice(new Slice.Builder("  3.5/10  ")
                .textColor(Color.WHITE)
                .setImageResource(R.mipmap.red_heart)
                .build());
        titleChooose.display();

        nineGridImageView = view.findViewById(R.id.grid_view);
        viewList = new ArrayList<>();
        viewList.add("http://i.imgur.com/DvpvklR.png");
        viewList.add("https://ps.ssl.qhmsg.com/bdr/576__/t01ca18554f214b9bb8.jpg");
        viewList.add("http://tu.maomaogougou.cn/picture/2015/05/d1ae13a28f8ef2dc2e6258ef2c513821.jpg");
        viewList.add("http://www.ixiupet.com/uploads/allimg/170729/110U2AU_0.jpg");
        nineGridImageView.setAdapter(mAdapter);
        nineGridImageView.setImagesData(viewList);

        flushBtn = view.findViewById(R.id.flushBtn);
        flushBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewList.clear();
                viewList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551334749910&di=b1bc26bf823d6bce1df5d565eec31ffe&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F382e0af337e355752b70bbba78524c4f384ee4c454ad5-3RKkaF_fw658");
                viewList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551334749910&di=9f364c05cba1835cfb17f37605833d30&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Fea52c1c9e5df4039ce5275095a0649921ad083244d20a-Lf82xv_fw658");
                viewList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551334749909&di=e671024983168b15331eef13361a1944&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2Ff9daed4b990f0ca0d21a4006675be1f595248ad1a322c-330xLp_fw658");
                viewList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551334749908&di=c7b112421c9187bae96ce76be4d85aa8&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fitbbs%2F1309%2F21%2Fc32%2F26062383_1379767339683_mthumb.jpg");
                nineGridImageView.setAdapter(mAdapter);
                nineGridImageView.setImagesData(viewList);
                nineGridImageView.invalidate();
            }
        });
    }

}
