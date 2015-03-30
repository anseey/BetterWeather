package com.anseey.betterweather.manager;

import android.content.Context;

import com.anseey.betterweather.model.WeatherMod;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by anseey on 15/3/29.
 */
public class WeatherManager {
    private static WeatherManager mInstance = new WeatherManager();
    private Context context;
    private static final String URL = "http://wthrcdn.etouch.cn/WeatherApi?citykey=";

    public static WeatherManager getInstance(Context context){
        mInstance.context = context;
        return mInstance;
    }

    private WeatherManager(){}

    public void getWeatherInfo(final String cityId, final OnWeatherInfoLoaded onWeatherInfoLoaded){

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    WeatherMod weatherMod = new WeatherMod();
                    Document doc = Jsoup.connect(URL+cityId).get();
                    System.out.println(doc.html());
                    weatherMod.setCity(doc.select("city").first().text());
                    weatherMod.setUpdatetime(doc.select("updatetime").first().text());
                    weatherMod.setShidu(doc.select("shidu").first().text());
                    weatherMod.setPm25(Integer.valueOf(doc.select("pm25").first().text()));
                    weatherMod.setQuality(doc.select("quality").first().text());
                    weatherMod.setDate(doc.select("date").first().text());
                    weatherMod.setLow(doc.select("low").first().text());
                    weatherMod.setHigh(doc.select("high").first().text());
                    weatherMod.setType(doc.select("type").first().text());
                    weatherMod.setFengxiang(doc.select("fengxiang").first().text());

                    onWeatherInfoLoaded.onFinish(weatherMod);
                } catch (IOException e1) {
//                                e1.printStackTrace();
                    onWeatherInfoLoaded.onError(e1);
                }

            }
        }).start();
    }

}
