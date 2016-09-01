package com.josealfonsomora.coolappsfacebook;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context context;


    public AppModule(App context) {
        this.context = context;
    }
    
}

