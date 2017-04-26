package com.sikware.FixMyLife.main;


import android.app.Application;

import com.sendbird.android.SendBird;

public class BaseApplication extends Application {

    //private static final String APP_ID = "9DA1B1F4-0BE6-4DA8-82C5-2E81DAB56F23"; // US-1 Demo
    private static final String APP_ID = "BDCD4D3E-2E3A-4CB7-A108-DB19B465B31F";
    public static final String VERSION = "3.0.30";

    @Override
    public void onCreate() {
        super.onCreate();
        SendBird.init(APP_ID, getApplicationContext());
    }
}
