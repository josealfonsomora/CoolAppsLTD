package com.josealfonsomora.coolappsfacebook;

import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.internal.util.RxThreadFactory;

@Module(includes = {
        AppModule.class
})
public class ClientModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(AppSettings.FACEBOOK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

    }

    @Provides
    public FacebookClient providesFacebookAPI(Retrofit retrofit) {
        return retrofit.create(FacebookClient.class);
    }

}
