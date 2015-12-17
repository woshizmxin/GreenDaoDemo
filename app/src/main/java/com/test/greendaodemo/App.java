package com.test.greendaodemo;

import android.app.Application;
import android.util.Log;

/**
 * Created by zhoumao on 15/12/16.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("tag","App");
    }
}
