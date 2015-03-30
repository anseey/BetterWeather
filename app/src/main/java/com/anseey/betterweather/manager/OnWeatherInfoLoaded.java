package com.anseey.betterweather.manager;

import com.anseey.betterweather.model.WeatherMod;

/**
 * Created by anseey on 15/3/29.
 */
public interface OnWeatherInfoLoaded {
    void onFinish(WeatherMod weatherMod);
    void onError(Exception e);
}
