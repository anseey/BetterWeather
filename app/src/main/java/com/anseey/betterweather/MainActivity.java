package com.anseey.betterweather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anseey.betterweather.manager.OnWeatherInfoLoaded;
import com.anseey.betterweather.manager.WeatherManager;
import com.anseey.betterweather.model.WeatherMod;
import com.anseey.betterweather.utils.NetUtil;
import com.anseey.betterweather.utils.PinYinUtils;
import com.anseey.betterweather.utils.PreferencesUtils;
import com.google.gson.Gson;

import java.lang.reflect.Field;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final int GET_WEATHER_SUCCESS = 1;
    private static final int GET_WEATHER_ERROR = -1;

//    private class MyHandler extends WeakReferenceHandler<MainActivity> {
//        public MyHandler(MainActivity weakReference) {
//            super(weakReference);
//        }
//
//        @Override
//        protected void handleMessage(MainActivity ref, Message msg) {
//            switch (msg.what) {
//                case GET_WEATHER_SUCCESS:
//                    //Toast.makeText(getApplicationContext(),mWeatherMod.getType(),Toast.LENGTH_SHORT).show();
//                    mTvCity.setText(mWeatherMod.getCity());
//                    mTvTime.setText("今天" + mWeatherMod.getUpdatetime() + "发布");
//                    mTvWet.setText("湿度:" + mWeatherMod.getShidu());
//                    if (mWeatherMod.getPm25() != 0) {
//                        mTvPM.setText(mWeatherMod.getPm25() + "");
//                        mTvAir.setText(mWeatherMod.getQuality());
//                    } else {
//                        mTvPM.setText("N/A");
//                        mTvAir.setText("N/A");
//                    }
//                    mTvDate.setText(mWeatherMod.getDate());
//                    mTvTemp.setText(mWeatherMod.getLow().substring(3) + "~" + mWeatherMod.getHigh().substring(3));
//                    mTvWeather.setText(mWeatherMod.getType());
//                    mTvCloud.setText(mWeatherMod.getFengxiang());
//                    if (mWeatherMod.getPm25() < 51) {
//                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_0_50));
//                    } else if (mWeatherMod.getPm25() < 101) {
//                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_51_100));
//                    } else if (mWeatherMod.getPm25() < 151) {
//                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_101_150));
//                    } else if (mWeatherMod.getPm25() < 201) {
//                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_151_200));
//                    } else if (mWeatherMod.getPm25() < 301) {
//                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_201_300));
//                    } else {
//                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_greater_300));
//                    }
//                    switch (mWeatherMod.getType()) {
//                        case "浮尘":
//                            mIvWeather.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_yin));
//                            break;
//                    }
//                    break;
//                case GET_WEATHER_ERROR:
//                    Toast.makeText(getApplicationContext(), "获取天气信息失败！", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    }
//
//    private MyHandler mh = new MyHandler(this);

    private Handler mHander = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_WEATHER_SUCCESS:
                    //Toast.makeText(getApplicationContext(),mWeatherMod.getType(),Toast.LENGTH_SHORT).show();
                    mTvCity.setText(mWeatherMod.getCity());
                    mTvTime.setText("今天" + mWeatherMod.getUpdatetime() + "发布");
                    mTvWet.setText("湿度:" + mWeatherMod.getShidu());
                    if (mWeatherMod.getPm25() != 0) {
                        mTvPM.setText(mWeatherMod.getPm25() + "");
                        mTvAir.setText(mWeatherMod.getQuality());
                    } else {
                        mTvPM.setText("N/A");
                        mTvAir.setText("N/A");
                    }
                    mTvDate.setText(mWeatherMod.getDate());
                    mTvTemp.setText(mWeatherMod.getLow().substring(3) + "~" + mWeatherMod.getHigh().substring(3));
                    mTvWeather.setText(mWeatherMod.getType());
                    mTvCloud.setText(mWeatherMod.getFengxiang());
                    if (mWeatherMod.getPm25() < 51) {
                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_0_50));
                    } else if (mWeatherMod.getPm25() < 101) {
                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_51_100));
                    } else if (mWeatherMod.getPm25() < 151) {
                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_101_150));
                    } else if (mWeatherMod.getPm25() < 201) {
                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_151_200));
                    } else if (mWeatherMod.getPm25() < 301) {
                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_201_300));
                    } else {
                        mIvAir.setImageDrawable(getApplicationContext().getResources().getDrawable(R.drawable.biz_plugin_weather_greater_300));
                    }

                    //update weather img
                    Class rClass = R.drawable.class;
                    try {
                        Field field = rClass.getField("biz_plugin_weather_"+ PinYinUtils.converterToSpell(mWeatherMod.getType()));
                        int vaule = (int) field.get(new Integer(0));
                        mIvWeather.setImageDrawable(getResources().getDrawable(vaule));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case GET_WEATHER_ERROR:
                    Toast.makeText(getApplicationContext(), "获取天气信息失败！", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    private WeatherManager mWeatherManager = WeatherManager.getInstance(this);
    private WeatherMod mWeatherMod;
    private TextView mTvCity;
    private TextView mTvTime;
    private TextView mTvWet;
    private TextView mTvPM;
    private TextView mTvAir;
    private TextView mTvDate;
    private TextView mTvTemp;
    private TextView mTvWeather;
    private TextView mTvCloud;
    private ImageView mIvAir;
    private ImageView mIvWeather;
    private boolean isUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvCity = (TextView) findViewById(R.id.tv_city);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvWet = (TextView) findViewById(R.id.tv_wet);
        mTvPM = (TextView) findViewById(R.id.tv_pm);
        mTvAir = (TextView) findViewById(R.id.tv_air);
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvTemp = (TextView) findViewById(R.id.tv_temp);
        mTvWeather = (TextView) findViewById(R.id.tv_weather);
        mTvCloud = (TextView) findViewById(R.id.tv_cloud);
        mIvAir = (ImageView) findViewById(R.id.iv_air);
        mIvWeather = (ImageView) findViewById(R.id.iv_weather);

        if (PreferencesUtils.getString(getApplicationContext(), "mWeatherMod") != null) {
            Gson gson = new Gson();
            mWeatherMod = gson.fromJson(PreferencesUtils.getString(getApplicationContext(), "mWeatherMod"), WeatherMod.class);
            mHander.sendEmptyMessage(GET_WEATHER_SUCCESS);
            isUpdate = false;
        }


    }


    public void clickUpdate(View v) {
        //TODO update
        if (NetUtil.isNetworkConnected(this)) {
            String cityId = PreferencesUtils.getString(getApplicationContext(), "number", "101010100");
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("加载天气信息中。。。");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
//            CityActivity.this;
            mWeatherManager.getWeatherInfo(cityId, new OnWeatherInfoLoaded() {
                @Override
                public void onFinish(WeatherMod weatherMod) {
                    mWeatherMod = weatherMod;
                    Gson gson = new Gson();
                    PreferencesUtils.putString(getApplicationContext(), "mWeatherMod", gson.toJson(mWeatherMod));
                    PreferencesUtils.putString(getApplicationContext(), "city", mWeatherMod.getCity());
                    mHander.sendEmptyMessage(GET_WEATHER_SUCCESS);
                    progressDialog.dismiss();
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    mHander.sendEmptyMessage(GET_WEATHER_ERROR);
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(this, "network error", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCity(View v) {
        Intent intent = new Intent(this, CityActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        if (isUpdate) {
            clickUpdate(null);
        } else {
            isUpdate = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }
}
