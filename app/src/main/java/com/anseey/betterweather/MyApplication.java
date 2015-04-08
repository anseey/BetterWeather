package com.anseey.betterweather;

import android.app.Application;

import com.anseey.betterweather.manager.CityManager;

/**
 * Created by anseey on 15/3/26.
 */
public class MyApplication extends Application{


    @Override
    public void onCreate() {
        super.onCreate();
        CityManager mCityManager = CityManager.getInstance(this);
        mCityManager.initDB();
//        mCityManager.getAllCity();
    }
}
