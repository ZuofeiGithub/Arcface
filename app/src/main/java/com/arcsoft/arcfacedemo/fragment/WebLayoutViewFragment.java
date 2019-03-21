package com.arcsoft.arcfacedemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arcsoft.arcfacedemo.R;
import com.blankj.utilcode.util.LogUtils;

import me.yokeyword.fragmentation.SupportFragment;

public class WebLayoutViewFragment extends SupportFragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.web_layout_view, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if(v != webView){
                        pop();
                    }
                }
                return false;
            }
        });
        webView = view.findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        Bundle bundle = getArguments();
        if(bundle != null){
            String test = bundle.getString("test");
            LogUtils.i(test);
        }
        webView.loadUrl("http://www.baidu.com");
    }

}
