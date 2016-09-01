package com.josealfonsomora.coolappsfacebook;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {

    void inject(App app);

    void inject(MainActivity mainActivity);

    public final static class Initializer {
        public static AppComponent init(App appContext) {
            return DaggerAppComponent.builder()
                    .appModule(new AppModule(appContext))
                    .build();
        }
    }
}
