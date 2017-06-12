package com.jicwiiaaa.aboutlife.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jicwiiaaa.aboutlife.R;
import com.jicwiiaaa.aboutlife.contract.MainContract;
import com.jicwiiaaa.aboutlife.model.WeatherInfoModel;
import com.jicwiiaaa.aboutlife.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.img_city_pic)
    ImageView imgCityPic;
    @BindView(R.id.tv_temperature)
    TextView tvTemperature;
    @BindView(R.id.tv_city)
    TextView tvCity;
    private MainPresenter mainPresenter;

    private List<WeatherInfoModel> weatherInfoModels = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mainPresenter = new MainPresenter(this);
        mainPresenter.getWeatherInfo();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showWeatherInfo(List<WeatherInfoModel> weatherInfoModels) {
        this.weatherInfoModels = weatherInfoModels;
        WeatherInfoModel weatherInfoModel = weatherInfoModels.get(currentIndex % MainPresenter.DEFAULT_NUM);
        Glide.with(this).load(weatherInfoModel.getThumb()).into(imgCityPic);
        tvCity.setText(weatherInfoModel.getTitle());

    }

    @OnClick({R.id.img_city_pic, R.id.tv_temperature, R.id.tv_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_city_pic:
                currentIndex ++;
                WeatherInfoModel weatherInfoModel = weatherInfoModels.get(currentIndex % MainPresenter.DEFAULT_NUM);
                if (weatherInfoModel != null) {
                    Glide.with(this).load(weatherInfoModel.getThumb()).into(imgCityPic);
                    tvCity.setText(weatherInfoModel.getTitle());
                }
                break;
            case R.id.tv_temperature:
                break;
            case R.id.tv_city:
                break;
        }
    }
}
