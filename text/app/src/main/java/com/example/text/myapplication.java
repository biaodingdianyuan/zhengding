package com.example.text;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 刘海风 on 2016/7/28.
 */

public class myapplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
