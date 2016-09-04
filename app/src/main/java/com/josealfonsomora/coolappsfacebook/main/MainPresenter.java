package com.josealfonsomora.coolappsfacebook.main;

import android.text.TextUtils;
import android.view.MenuItem;

import com.josealfonsomora.coolappsfacebook.R;
import com.josealfonsomora.coolappsfacebook.UserSession;
import com.josealfonsomora.coolappsfacebook.facebookAPI.Data;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebooFilmProfile;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookClient;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookPictureType;
import com.josealfonsomora.coolappsfacebook.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainView> {
    private final FacebookClient facebookClient;
    private final UserSession userSession;
    private List<FacebooFilmProfile> films = new ArrayList<>();

    public MainPresenter(FacebookClient facebookClient, UserSession userSession) {
        this.facebookClient = facebookClient;
        this.userSession = userSession;
    }

    private void getUserPictureProfile() {
        int px = 300;
        facebookClient.getUserPictureProfile(
                userSession.getFacebookUserId(),
                userSession.getFacebookAccessToken(),
                FacebookPictureType.ALBUM, px, px)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response != null) {
                                    if (response.getData() != null) {
                                        userSession.setPictureUrl(response.getData().getUrl());
                                        getView().setUserPicture(response.getData().getUrl());
                                    }
                                }
                            }
                        },
                        error -> {
                            if (isViewAttached()) {

                                getView().showErrorToast(error.getMessage());
                            }
                        });
    }

    private void getUserPublicProfile() {
        facebookClient.getUserPublicProfile(userSession.getFacebookUserId(), userSession.getFacebookAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response != null) {
                                    if (!TextUtils.isEmpty(response.getName())) {
                                        userSession.setUserName(response.getName());
                                        getView().setUserName(response.getName());
                                    }
                                    if (!TextUtils.isEmpty(response.getGender())) {
                                        userSession.setGender(response.getGender());
                                        getView().setUserGender(response.getGender());
                                    }
                                    if (!TextUtils.isEmpty(response.getLink())) {
                                        userSession.setLink(response.getLink());
                                        getView().setUserLink(response.getLink());
                                    }

                                }
                            }
                        },
                        error -> {
                            if (isViewAttached()) {
                                getView().showErrorToast(error.getMessage());
                            }
                        });
    }

    public void onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id != R.id.nav_user_profile) {
            if (isViewAttached()) {
                getView().closeNavDrawer();
            }
        }

        if (id == R.id.nav_user_profile) {
            // Handle the camera action
            if (isViewAttached()) {
                getView().openUserProfile();
            }
        } else if (id == R.id.nav_gallery) {
            getFilms();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            if (isViewAttached()) {
                userSession.clear();
                getView().moveToLogin();
            }
        }
    }

    private void getFilms() {
        facebookClient.getUserFilms(userSession.getFacebookUserId(), userSession.getFacebookAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(response -> {
                            if (isViewAttached()) {
                                if (response.getData() != null) {
                                    for (Data data : response.getData()) {
                                        facebookClient.getFilmProfile(data.getId(), userSession.getFacebookAccessToken())
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .unsubscribeOn(Schedulers.io())
                                                .subscribe(dataResponse -> {
                                                            if (isViewAttached()) {
                                                                if (dataResponse != null) {
                                                                    films.add(dataResponse);
                                                                    getView().addNewFilm(dataResponse);
                                                                }
                                                            }
                                                        },
                                                        error -> {
                                                            if (isViewAttached()) {
                                                                getView().showErrorToast(error.getMessage());
                                                            }
                                                        });
                                    }
                                }
                            }
                        },
                        error -> {
                            if (isViewAttached()) {
                                getView().showErrorToast(error.getMessage());
                            }
                        });
    }

    public void init() {
        getUserPublicProfile();
        getUserPictureProfile();
    }
}
