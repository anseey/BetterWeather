package com.anseey.betterweather.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.anseey.betterweather.model.CityMod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anseey on 15/4/5.
 */
public class CityManager {
    public static SQLiteDatabase mDB;
    private static CityManager mInstance = new CityManager();
    private Context mContext;

    private CityManager() {
    }

    public static CityManager getInstance(Context context) {
        mInstance.mContext = context;
        return mInstance;
    }

    public void initDB() {
        System.out.println("CityManager.initDB");
        String path = "/data" + Environment.getDataDirectory().getAbsolutePath()
                + File.separator + mContext.getPackageName() + File.separator + "databases";
        System.out.println("path = " + path);
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        path = path + File.separator + "city.db";
        file = new File(path);
        if (!file.exists()) {

            try {
                InputStream is = mContext.getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(file);
                int len = -1;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf))!=-1){
                    fos.write(buf, 0, len);
                }
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                file.delete();
            }
        } else {
            System.out.println("DB file exists!");
        }

        mDB = mContext.openOrCreateDatabase("city.db", Context.MODE_PRIVATE, null);
    }

    public List<CityMod> getAllCity() {
        List<CityMod> list= new ArrayList<CityMod>();
        Cursor c = mDB.query("city", null, null, null ,null ,null, null);
        System.out.println(c.getCount());

        while (c.moveToNext()){
            String province = c.getString(c.getColumnIndex("province"));
            String city = c.getString(c.getColumnIndex("city"));
            String number = c.getString(c.getColumnIndex("number"));
            String allPY = c.getString(c.getColumnIndex("allpy"));
            String allFirstPY = c.getString(c.getColumnIndex("allfirstpy"));
            String firstPY = c.getString(c.getColumnIndex("firstpy"));
            CityMod cityMod = new CityMod(province, city, number, firstPY, allPY, allFirstPY);
            list.add(cityMod);
        }
        return list;
    }

}
