package com.josealfonsomora.coolappsfacebook;

import com.josealfonsomora.coolappsfacebook.login.LoginActivity;
import com.josealfonsomora.coolappsfacebook.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        ClientModule.class
})
public interface AppComponent {

    void inject(App app);

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    public final static class Initializer {
        public static AppComponent init(App appContext) {
            return DaggerAppComponent.builder()
                    .appModule(new AppModule(appContext))
                    .build();
        }
    }
}
