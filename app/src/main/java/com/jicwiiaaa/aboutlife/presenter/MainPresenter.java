package com.jicwiiaaa.aboutlife.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;
import com.jicwiiaaa.aboutlife.contract.MainContract;
import com.jicwiiaaa.aboutlife.helper.SysCons;
import com.jicwiiaaa.aboutlife.model.BannerPic;
import com.jicwiiaaa.aboutlife.model.FunnyInfo;
import com.jicwiiaaa.aboutlife.model.WeatherInfoModel;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by liuliu on 2017/6/8.
 */

public class MainPresenter implements MainContract.Presenter {
    public final static int DEFAULT_NUM = 10;

    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        getWeatherInfo();
    }

    @Override
    public void getWeatherInfo() {
        //做业务请求
        LogUtils.d("getWeatherInf");
        OkGo.get("http://route.showapi.com/197-1")
                .tag(this)
                .params("showapi_appid", SysCons.SHOWAPI_APPID)
                .params("showapi_sign", SysCons.SHOWAPI_SECRET)
                .params("rand", 1)
                .params("num", DEFAULT_NUM)
                .params("page", 1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonData = new JSONObject(s);
                            Gson gson = new Gson();
                            if (jsonData.optInt("showapi_res_code") == 0) {
                                List<WeatherInfoModel> weatherInfoModels = new ArrayList<WeatherInfoModel>();
                                JSONArray arrayData = jsonData.getJSONObject("showapi_res_body").optJSONArray("newslist");
                                for (int i = 0; i < arrayData.length(); i++) {
                                    WeatherInfoModel weatherInfoModel = gson.fromJson(arrayData.getJSONObject(i).toString(), WeatherInfoModel.class);
                                    weatherInfoModels.add(weatherInfoModel);
                                }

                                view.showWeatherInfo(weatherInfoModels);
                            }else {
                                ToastUtils.showShort(jsonData.optString("showapi_res_error"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    @Override
    public void getBannerPic() {
        OkGo.get("http://route.showapi.com/1377-1")
                .tag(this)
                .params("showapi_appid", SysCons.SHOWAPI_APPID)
                .params("showapi_sign", SysCons.SHOWAPI_SECRET)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonData = new JSONObject(s);
                            Gson gson = new Gson();
                            if (jsonData.optInt("showapi_res_code") == 0) {
                                List<BannerPic> bannerPics = new ArrayList<BannerPic>();
                                JSONArray arrayData = jsonData.getJSONObject("showapi_res_body").optJSONArray("list");
                                for (int i = 0; i < arrayData.length(); i++) {
                                    BannerPic bannerPic = gson.fromJson(arrayData.getJSONObject(i).toString(), BannerPic.class);
                                    bannerPics.add(bannerPic);
                                }

                                view.showBannerPic(bannerPics);
                            }else {
                                ToastUtils.showShort(jsonData.optString("showapi_res_error"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    @Override
    public void getFunnyInfos(int page) {
        OkGo.get("http://route.showapi.com/255-1")
                .tag(this)
                .params("showapi_appid", SysCons.SHOWAPI_APPID)
                .params("showapi_sign", SysCons.SHOWAPI_SECRET)
                .params("type", 10)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsonData = new JSONObject(s);
                            Gson gson = new Gson();
                            if (jsonData.optInt("showapi_res_code") == 0) {
                                List<FunnyInfo> funnyInfos = new ArrayList<FunnyInfo>();
                                JSONArray arrayData = jsonData.getJSONObject("showapi_res_body").optJSONObject("pagebean").optJSONArray("contentlist");
                                for (int i = 0; i < arrayData.length(); i++) {
                                    FunnyInfo funnyInfo = gson.fromJson(arrayData.getJSONObject(i).toString(), FunnyInfo.class);
                                    funnyInfos.add(funnyInfo);
                                }

                                view.showFunnyInfos(funnyInfos);

                            }else {
                                ToastUtils.showShort(jsonData.optString("showapi_res_error"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}
