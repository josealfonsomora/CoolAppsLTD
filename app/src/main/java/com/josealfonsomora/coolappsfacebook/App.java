package com.josealfonsomora.coolappsfacebook;

import android.app.Application;

import com.facebook.FacebookSdk;

public class App extends Application {
    private static App context;
    private AppComponent component;

    public static App getApp() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        component = AppComponent.Initializer.init(this);
        component.inject(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        FacebookSdk.setIsDebugEnabled(true);
    }

    public AppComponent component() {
        return component;
    }
}
