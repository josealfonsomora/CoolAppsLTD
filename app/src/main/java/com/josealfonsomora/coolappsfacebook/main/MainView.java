package com.josealfonsomora.coolappsfacebook.main;

import com.josealfonsomora.coolappsfacebook.mvp.MvpView;

public interface MainView extends MvpView {
    void setUserPicture(String response);

    void setUserName(String name);

    void setUserGender(String gender);

    void setUserLink(String link);

    void showErrorToast(String message);
}
