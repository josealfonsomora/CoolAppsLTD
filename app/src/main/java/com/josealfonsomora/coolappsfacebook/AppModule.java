package com.josealfonsomora.coolappsfacebook;

import android.content.Context;

import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookClient;
import com.josealfonsomora.coolappsfacebook.login.LoginPresenter;
import com.josealfonsomora.coolappsfacebook.main.MainPresenter;
import com.josealfonsomora.coolappsfacebook.profile.ProfilePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context context;


    public AppModule(App context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public UserSession provideAppSettings() {
        return new UserSession(context);
    }

    @Provides
    public LoginPresenter provideLoginPresenter(UserSession userSession) {
        return new LoginPresenter(userSession);
    }

    @Provides
    public MainPresenter provideMainPresenter(FacebookClient facebookClient, UserSession userSession) {
        return new MainPresenter(facebookClient, userSession);
    }

    @Provides
    public ProfilePresenter provideProfilePresenter(FacebookClient facebookClient, UserSession userSession) {
        return new ProfilePresenter(userSession,facebookClient);
    }

}

