package com.pjh.lotto;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {
    private static Context applicationContext;

    public static Context getAppContext() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }
}
