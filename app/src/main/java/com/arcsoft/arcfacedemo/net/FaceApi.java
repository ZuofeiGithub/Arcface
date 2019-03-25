package com.arcsoft.arcfacedemo.net;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.arcsoft.arcfacedemo.constants.Auth;
import com.arcsoft.arcfacedemo.model.Banner;
import com.arcsoft.arcfacedemo.net.json.FaceRespData;
import com.arcsoft.arcfacedemo.constants.Device;
import com.arcsoft.arcfacedemo.constants.Url;
import com.arcsoft.arcfacedemo.util.ACache;
import com.arcsoft.arcfacedemo.util.ImageUtil;
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
                    registerFace(face,String.valueOf(System.currentTimeMillis()),String.valueOf(System.currentTimeMillis()));
                } else {
                    LogUtils.i("找到人脸", error_code + ":" + error_msg);
                    LogUtils.i(new String(responseBody));
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

    public void verifyFace(Bitmap face){
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
                    LogUtils.i(data.getResult().getFace_list().get(0).getLandmark().get(0).getX(),data.getResult().getFace_list().get(0).getLandmark().get(0).getX());
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

    public void getToken(String username, String password, final ITokenCallBack tokenCallBack){
        RequestParams params = new RequestParams();
        params.put("username",username);
        params.put("password",password);
        client.post(Url.IP + "/api/auth", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONObject jsonObject = JSONObject.parseObject(new String(responseBody));
                final String access_token = jsonObject.getString("token");
                tokenCallBack.accessToken(access_token);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void getBanners(String token, final IBannerCallBack bannerCallBack){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bearer ");
        stringBuilder.append(token);
        client.addHeader("Authorization", stringBuilder.toString());
        client.get(Url.IP + "/api/bannerlist", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                JSONArray jsonArray = JSONArray.parseArray(new String(responseBody));
                List<Banner> bannerList = JSON.parseArray(jsonArray.toJSONString(),Banner.class);
                bannerCallBack.banner(bannerList);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
