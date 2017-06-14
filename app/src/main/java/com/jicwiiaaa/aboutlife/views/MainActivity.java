package com.jicwiiaaa.aboutlife.views;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.jicwiiaaa.aboutlife.R;
import com.jicwiiaaa.aboutlife.contract.MainContract;
import com.jicwiiaaa.aboutlife.model.BannerPic;
import com.jicwiiaaa.aboutlife.model.WeatherInfoModel;
import com.jicwiiaaa.aboutlife.presenter.MainPresenter;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.img_bg_banner)
    ImageView imgBgBanner;
    private MainPresenter mainPresenter;

    private List<WeatherInfoModel> weatherInfoModels = new ArrayList<>();
    private List<BannerPic> bannerPics = new ArrayList<>();

    private int basePosition = 0;//banner开源项目在滑动时，position异常，需添加一个basePosition

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
//        mainPresenter.getWeatherInfo();
        mainPresenter.getBannerPic();

        initViews();
        initListener();
    }

    private void initViews() {
        Glide.with(this)
                .load(R.mipmap.bg_default_city)
                
                .bitmapTransform(new BlurTransformation(this, 22, 3))// “12”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                .into(imgBgBanner);
    }

    private void initListener() {
        banner.addPageChangeLisnter(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LogUtils.d("onPageScrolled");
                LogUtils.d("position-->" + position);
                if (basePosition == 0) {
                    basePosition = position;
                }
            }

            @Override
            public void onPageSelected(int position) {//开源项目问题，此处position+2500
                Glide.with(MainActivity.this)
                        .load(bannerPics.get((position + bannerPics.size() - basePosition) % bannerPics.size()).getPic())
                        .placeholder(R.mipmap.bg_default_city)
                        
                        .bitmapTransform(new BlurTransformation(MainActivity.this, 22, 3))// “12”：设置模糊度(在0.0到25.0之间)，默认”25";"4":图片缩放比例,默认“1”。
                        .into(imgBgBanner);
                LogUtils.d("onPageSelected");
                LogUtils.d("position-->" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtils.d("onPageScrollStateChanged");
                LogUtils.d("state-->" + state);
            }
        });

        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                LogUtils.d("position-->" + i);
//                // 使用CustomTabsIntent.Builder配置CustomTabsIntent
//                // 准备完成后，调用CustomTabsIntent.Builder.build()方法创建一个CustomTabsIntent
//                // 并通过CustomTabsIntent.launchUrl()方法加载希望加载的url
//
//                String url = weatherInfoModels.get(i).getUrl();
//                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
//                builder.setToolbarColor(Color.BLUE);
//                CustomTabsIntent customTabsIntent = builder.build();
//                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
            }
        });
    }


    @Override
    protected boolean translucentStatusBar() {
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.pause();
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


    }

    @Override
    public void showBannerPic(List<BannerPic> bannerPics) {
        this.bannerPics = bannerPics;
        // 设置数据
        banner.setPages(bannerPics, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }

    public static class BannerViewHolder implements MZViewHolder<BannerPic> {

        ImageView imgCityPic;
        TextView tvTemperature;
        TextView tvCity;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_main_banner, null);
            imgCityPic = (ImageView) view.findViewById(R.id.img_city_pic);
            tvTemperature = (TextView) view.findViewById(R.id.tv_temperature);
            tvCity = (TextView) view.findViewById(R.id.tv_city);
            return view;
        }

        @Override
        public void onBind(Context context, int position, BannerPic data) {
            // 数据绑定
            Glide.with(context).load(data.getPic()).into(imgCityPic);
            tvCity.setText(data.getTitle());
        }
    }
}
