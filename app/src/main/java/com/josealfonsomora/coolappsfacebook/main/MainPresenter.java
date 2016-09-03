package com.josealfonsomora.coolappsfacebook.main;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.josealfonsomora.coolappsfacebook.UserSession;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookClient;
import com.josealfonsomora.coolappsfacebook.mvp.BaseRxPresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BaseRxPresenter<MainView> {
    private final FacebookClient facebookClient;
    private final UserSession userSession;

    public MainPresenter(FacebookClient facebookClient, UserSession userSession) {
        this.facebookClient = facebookClient;
        this.userSession = userSession;
    }

    @Override
    public void attachView(@NonNull MainView view) {
        super.attachView(view);
        resetRxOperation();
        facebookClient.getUserPublicProfile(userSession.getFacebookUserId(), userSession.getFacebookAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .compose(cancelOperationTransformer())
                .subscribe(response -> {
                            if (isViewAttached()) {
                                if(response!=null) {
                                    if (response.getPicture() != null) {
                                        if (response.getPicture().getData() != null) {
                                            getView().setUserPicture(response.getPicture().getData().getUrl());

                                        }
                                    }
                                    if(!TextUtils.isEmpty(response.getName())){
                                            getView().setUserName(response.getName());
                                    }
                                    if(!TextUtils.isEmpty(response.getGender())){
                                        getView().setUserGender(response.getGender());
                                    }
                                    if(!TextUtils.isEmpty(response.getLink())){
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
