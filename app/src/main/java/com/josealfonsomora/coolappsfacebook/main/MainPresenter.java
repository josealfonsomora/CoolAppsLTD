package com.josealfonsomora.coolappsfacebook.main;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.josealfonsomora.coolappsfacebook.UserSession;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookClient;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookPictureType;
import com.josealfonsomora.coolappsfacebook.mvp.BasePresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainView> {
    private final FacebookClient facebookClient;
    private final UserSession userSession;

    public MainPresenter(FacebookClient facebookClient, UserSession userSession) {
        this.facebookClient = facebookClient;
        this.userSession = userSession;
    }

    @Override
    public void attachView(@NonNull MainView view) {
        super.attachView(view);
//        resetRxOperation();
        getUserPublicProfile();
        getUserPictureProfile();

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
                                        getView().setUserName(response.getName());
                                    }
                                    if (!TextUtils.isEmpty(response.getGender())) {
                                        getView().setUserGender(response.getGender());
                                    }
                                    if (!TextUtils.isEmpty(response.getLink())) {
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
}
