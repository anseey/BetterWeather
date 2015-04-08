package com.anseey.betterweather.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by anseey on 15/4/8.
 */
public abstract  class WeakReferenceHandler<T> extends Handler{
    private WeakReference<T> mWeakReference;

    public WeakReferenceHandler(T weakReference){
        mWeakReference = new WeakReference<T>(weakReference);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mWeakReference.get()==null)
        {
            return;
        }
        handleMessage(mWeakReference.get(), msg);
    }

    protected abstract void handleMessage(T ref, Message msg);

}
