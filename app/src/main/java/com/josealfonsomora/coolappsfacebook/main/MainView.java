package com.josealfonsomora.coolappsfacebook.main;

import com.github.mikephil.charting.data.PieEntry;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookFilmProfile;
import com.josealfonsomora.coolappsfacebook.mvp.MvpView;

import java.util.ArrayList;
import java.util.List;

public interface MainView extends MvpView {
    void setUserPicture(String response);

    void setUserName(String name);

    void setUserGender(String gender);

    void setUserLink(String link);

    void showErrorToast(String message);

    void moveToLogin();

    void openUserProfile();

    void closeNavDrawer();

    void initChart();

    void setChartData(ArrayList<PieEntry> entries, String title);

    void showNoDataFromFacebookErrorToast(String object);
}
