package com.anseey.betterweather;

import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvCity = (TextView)findViewById(R.id.tv_city);
        mTvTime = (TextView)findViewById(R.id.tv_time);
        mTvWet = (TextView)findViewById(R.id.tv_wet);
        mTvPM = (TextView)findViewById(R.id.tv_pm);
        mTvAir = (TextView)findViewById(R.id.tv_air);
        mTvDate = (TextView)findViewById(R.id.tv_date);
        mTvTemp = (TextView)findViewById(R.id.tv_temp);
        mTvWeather = (TextView)findViewById(R.id.tv_weather);
        mTvCloud = (TextView)findViewById(R.id.tv_cloud);
        mIvAir = (ImageView)findViewById(R.id.iv_air);
        mIvWeather = (ImageView)findViewById(R.id.iv_weather);

        Button mb = new Button(this);

        XmlResourceParser mXmlResourceParser = getResources().getXml(R.xml.activity_mainq);


    }

    public void clickUpdate(View v) {
        //TODO update
    }

    public void onClickCity(View v) {
        //TODO update
        Intent intent = new Intent(this,CityActivity.class);
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
