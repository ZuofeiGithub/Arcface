package com.arcsoft.arcfacedemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.arcsoft.arcfacedemo.R;
import com.blankj.utilcode.util.ImageUtils;
import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.glview.GLView;

public class MyGLView extends GLView {
    public MyGLView(Context context) {
        super(context);
    }

    public MyGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onGLDraw(ICanvasGL canvas) {


        Bitmap testbitmap = ImageUtils.getBitmap(R.mipmap.ic_boy);
        canvas.drawBitmap(testbitmap,0,0,300,300);
    }
}
