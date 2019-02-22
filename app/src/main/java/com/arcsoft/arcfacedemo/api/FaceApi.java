package com.arcsoft.arcfacedemo.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arcsoft.arcfacedemo.api.json.FaceRespData;
import com.arcsoft.arcfacedemo.constants.Device;
import com.arcsoft.arcfacedemo.constants.Url;
import com.arcsoft.arcfacedemo.util.ImageUtil;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FaceApi {

    private Gson gson;
    private GsonBuilder builder;

    private static  FaceApi instance = null;
    private  AsyncHttpClient client;
    private FaceApi(){
        client = new AsyncHttpClient();
        builder = new GsonBuilder();
        gson = builder.create();
        LogUtils.getConfig().setGlobalTag("结果");
    }

    public static FaceApi getInstance() {
        if(instance == null){
            instance = new FaceApi();
        }
        return instance;
    }

    /**
     * 人脸搜索
     *
     * @param face
     */
    public void faceSearch(final Bitmap face) {
        verifyToken();
        RequestParams params = new RequestParams();
        params.put("deviceId", Device.ID);
        params.put("image", ImageUtil.bitmapToBase64(face));
        params.put("group_id", Device.ID.substring(0, 8));

        client.post(Url.IP + "/api/face_search", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject = JSONObject.parseObject(new String(responseBody));
                Integer error_code = jsonObject.getInteger("error_code");
                String error_msg = jsonObject.getString("error_msg");
                if (error_code != 0) {
                    LogUtils.i("没有找到人脸", error_code + ":" + error_msg);
                } else {
                    LogUtils.i("找到人脸", error_code + ":" + error_msg);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LogUtils.i("没找到人脸", statusCode + ":" + error.getMessage());
            }
        });
    }


    public void registerFace(Bitmap face,String user_id,String user_info) {
        verifyToken();
        RequestParams params = new RequestParams();
        params.put("deviceId", Device.ID);
        params.put("image", ImageUtil.bitmapToBase64(face));
        params.put("group_id", Device.ID.substring(0, 8));
        params.put("user_id", user_id);
        params.put("user_info", user_info);
        client.post(Url.IP + "/api/register_face", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                LogUtils.i(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LogUtils.i("注册失败", statusCode + ":" + error.getMessage());
            }
        });

    }

    public void verifyFace(Bitmap face, final Context context){
        verifyToken();
        RequestParams params = new RequestParams();
        params.put("deviceId", Device.ID);
        params.put("image", ImageUtil.bitmapToBase64(face));
        params.put("group_id", Device.ID.substring(0, 8));
        client.post(Url.IP + "/api/face_verify", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    FaceRespData data = gson.fromJson(new String(responseBody),FaceRespData.class);
                    LogUtils.i(data.getResult().getFace_list().get(0).getAge());
                }catch (Exception e){
                    ToastUtils.showShort("有错误出现");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LogUtils.i("活体检测失败", statusCode + ":" + error.getMessage());
            }
        });
    }

    private void verifyToken(){
        RequestParams params = new RequestParams();
        params.put("deviceId", Device.ID);
        client.post(Url.IP+"/api/updateToken",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
               LogUtils.i(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                LogUtils.i("失败:"+"错误码:"+statusCode+" 消息:"+error.getMessage());
            }
        });
    }
}
