package com.josealfonsomora.coolappsfacebook;

import android.app.Application;

public class App extends Application {
    private static App context;

    public static App getApp() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }
}
