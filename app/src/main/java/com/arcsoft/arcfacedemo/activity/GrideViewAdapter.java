package com.arcsoft.arcfacedemo.activity;

import android.content.Context;
import android.widget.ImageView;

import com.jaeger.ninegridimageview.GridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;

import java.util.List;

class GrideViewAdapter<T> extends NineGridImageViewAdapter<T> {
    @Override
    protected void onDisplayImage(Context context, ImageView imageView, T o) {

    }

    @Override
    protected void onItemImageClick(Context context, ImageView imageView, int index, List list) {
        super.onItemImageClick(context, imageView, index, list);
    }

    @Override
    protected ImageView generateImageView(Context context) {
        GridImageView imageView = new GridImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
