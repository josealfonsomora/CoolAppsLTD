package com.josealfonsomora.coolappsfacebook.mvp;

import android.support.annotation.NonNull;

public interface Presenter<V extends MvpView> {

    void attachView(@NonNull V view);

    void detachView();

}