package com.josealfonsomora.coolappsfacebook.profile;

import com.josealfonsomora.coolappsfacebook.mvp.MvpView;

public interface ProfileView extends MvpView{
    void updateCover(String source);

    void updateGender(String gender);

    void updateEmail(String email);

    void updateBirthday(String birthday);

    void updateQuote(String quotes);

    void updateName(String name);
}
