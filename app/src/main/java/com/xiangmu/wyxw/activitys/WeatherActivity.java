package com.xiangmu.wyxw.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.xiangmu.wyxw.Bean.WeatherBean;
import com.xiangmu.wyxw.R;
import com.xiangmu.wyxw.Setting_Utils.ShareUtils;

import java.util.List;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton mWeather_back;
    private ImageButton mWeather_location;
    private ImageButton mWeather_share;
    private ImageView mWeather_icon;
    private ImageView mWeather_icon1;
    private ImageView mWeather_icon2;
    private ImageView mWeather_icon3;
    private TextView mJintian_temperature;
    private TextView mJintian_date;
    private TextView mJintian_pm25;
    private TextView mJintian_weather;
    private TextView mJintian_wind;
    private TextView mMingtian;
    private TextView mMingtian_temperature;
    private TextView mMingtian_weather;
    private TextView mHoutian;
    private TextView mHoutian_temperature;
    private TextView mHoutian_weather;
    private TextView mDahoutian;
    private TextView mDahoutian_temperature;
    private TextView mDahoutian_weather;
    private WeatherBean weatherBean;
    private BitmapUtils bitmapUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        weatherBean = (WeatherBean) bundle.getSerializable("weatherBean");
        bindViews();
        getData();
    }

    // End Of Content View Elements
    private void bindViews() {
        mWeather_back = (ImageButton) findViewById(R.id.weather_back);
        mWeather_location = (ImageButton) findViewById(R.id.weather_location);
        mWeather_share = (ImageButton) findViewById(R.id.weather_share);

        mWeather_back.setOnClickListener(this);
        mWeather_location.setOnClickListener(this);
        mWeather_share.setOnClickListener(this);
        //今天
        mWeather_icon = (ImageView) findViewById(R.id.weather_icon);
        mJintian_temperature = (TextView) findViewById(R.id.jintian_temperature);
        mJintian_date = (TextView) findViewById(R.id.jintian_date);
        mJintian_pm25 = (TextView) findViewById(R.id.jintian_pm25);
        mJintian_weather = (TextView) findViewById(R.id.jintian_weather);
        mJintian_wind = (TextView) findViewById(R.id.jintian_wind);
        //明天
        mWeather_icon1 = (ImageView) findViewById(R.id.weather_icon1);
        mMingtian = (TextView) findViewById(R.id.mingtian);
        mMingtian_temperature = (TextView) findViewById(R.id.mingtian_temperature);
        mMingtian_weather = (TextView) findViewById(R.id.mingtian_weather);
        //后天
        mWeather_icon2 = (ImageView) findViewById(R.id.weather_icon2);
        mHoutian = (TextView) findViewById(R.id.houtian);
        mHoutian_temperature = (TextView) findViewById(R.id.houtian_temperature);
        mHoutian_weather = (TextView) findViewById(R.id.houtian_weather);
        //大后天
        mWeather_icon3 = (ImageView) findViewById(R.id.weather_icon3);
        mDahoutian = (TextView) findViewById(R.id.dahoutian);
        mDahoutian_temperature = (TextView) findViewById(R.id.dahoutian_temperature);
        mDahoutian_weather = (TextView) findViewById(R.id.dahoutian_weather);
    }
    //获取天气数据
     String pm25;  String date;  String dayPictureUrl; String weather; String wind; String temperature;
    private void getData() {
        List<WeatherBean.ResultsEntity.Weather_dataEntity> weatherdata = weatherBean.results.get(0).weather_data;
        //今天
        WeatherBean.ResultsEntity.Weather_dataEntity dataEntity1 = weatherdata.get(0);
        pm25 = weatherBean.results.get(0).pm25;
        date = dataEntity1.date;
        dayPictureUrl = dataEntity1.dayPictureUrl;
        weather = dataEntity1.weather;
        wind = dataEntity1.wind;
        temperature = dataEntity1.temperature;

        mJintian_temperature.setText(temperature);
        mJintian_date.setText(date);
        mJintian_pm25.setText(pm25);
        mJintian_weather.setText(weather);
        mJintian_wind.setText(wind);
        bitmapUtils = new BitmapUtils(this);
        bitmapUtils.display(mWeather_icon, dayPictureUrl);

        //明天
        WeatherBean.ResultsEntity.Weather_dataEntity dataEntity2 = weatherdata.get(1);
        String dayPictureUrl2 = dataEntity1.dayPictureUrl;
        String date2 = dataEntity2.date;
        String temperature2 = dataEntity2.temperature;
        String weather2 = dataEntity2.weather;
        String wind2 = dataEntity2.wind;
        mMingtian.setText(date2);
        mMingtian_temperature.setText(temperature2);
        mMingtian_weather.setText(weather2+" "+wind2);
        bitmapUtils.display(mWeather_icon1, dayPictureUrl2);

        //后天
        WeatherBean.ResultsEntity.Weather_dataEntity dataEntity3 = weatherdata.get(2);
        String dayPictureUrl3 = dataEntity3.dayPictureUrl;
        String date3 = dataEntity3.date;
        String temperature3 = dataEntity3.temperature;
        String weather3 = dataEntity3.weather;
        String wind3 = dataEntity3.wind;
        mHoutian.setText(date3);
        mHoutian_temperature.setText(temperature3);
        mHoutian_weather.setText(weather3 + " " + wind3);
        bitmapUtils.display(mWeather_icon2, dayPictureUrl3);

        //大后天
        WeatherBean.ResultsEntity.Weather_dataEntity dataEntity4 = weatherdata.get(3);
        String dayPictureUrl4 = dataEntity4.dayPictureUrl;
        String date4 = dataEntity4.date;
        String temperature4 = dataEntity4.temperature;
        String weather4 = dataEntity4.weather;
        String wind4 = dataEntity4.wind;
        mDahoutian.setText(date4);
        mDahoutian_temperature.setText(temperature4);
        mDahoutian_weather.setText(weather4 + " " + wind4);
        bitmapUtils.display(mWeather_icon3, dayPictureUrl4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmapUtils!= null){
            bitmapUtils.cancel();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.weather_back:
                WeatherActivity.this.finish();
                break;
            case R.id.weather_location:
                Toast.makeText(this, "请选择城市", Toast.LENGTH_SHORT).show();
                break;
            case R.id.weather_share:// TODO: 2015/11/14 加分享 分享链接
                ShareUtils.shareContent(this,"冒泡天气指数预报:数据来自中国天气 http://www.weather.com.cn/weather1d/101180101.shtml"+"\n"+"分享当前郑州天气:" + "今日:"+date+"\n"+"PM2.5:"+pm25+"\n"+"天气状况:"+weather+" "+wind,"http://img3.imgtn.bdimg.com/it/u=1318680829,3666649020&fm=21&gp=0.jpg");
                break;
        }
    }
}
