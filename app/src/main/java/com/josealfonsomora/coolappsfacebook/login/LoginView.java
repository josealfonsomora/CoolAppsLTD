package com.josealfonsomora.coolappsfacebook.login;

import com.josealfonsomora.coolappsfacebook.mvp.MvpView;

public interface LoginView extends MvpView {
    void moveToMain();

    void showFacebookError(String message);

    void showMushLoginWithFacebookToContinueError();
}
