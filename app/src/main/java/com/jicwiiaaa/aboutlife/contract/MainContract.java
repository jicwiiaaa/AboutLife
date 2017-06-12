package com.jicwiiaaa.aboutlife.contract;

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
    }

    interface Presenter extends BasePresenter {
        void getWeatherInfo();

    }
}
