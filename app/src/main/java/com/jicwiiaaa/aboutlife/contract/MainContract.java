package com.jicwiiaaa.aboutlife.contract;

import com.jicwiiaaa.aboutlife.model.BannerPic;
import com.jicwiiaaa.aboutlife.model.FunnyInfo;
import com.jicwiiaaa.aboutlife.model.WeatherInfoModel;
import com.jicwiiaaa.aboutlife.presenter.BasePresenter;

import java.util.List;

import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/8.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showWeatherInfo(List<WeatherInfoModel> weatherInfoModels);

        void showBannerPic(List<BannerPic> bannerPics);

        void showFunnyInfos(List<FunnyInfo> funnyInfos);
    }

    interface Presenter extends BasePresenter {
        void getWeatherInfo();

        void getBannerPic();

        void getFunnyInfos(int page);

    }
}
