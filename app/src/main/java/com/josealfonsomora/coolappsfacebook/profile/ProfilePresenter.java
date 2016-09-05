package com.josealfonsomora.coolappsfacebook.profile;

import android.text.TextUtils;

import com.josealfonsomora.coolappsfacebook.UserSession;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookClient;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookPublicProfile;
import com.josealfonsomora.coolappsfacebook.mvp.BasePresenter;

public class ProfilePresenter extends BasePresenter<ProfileView> {
    private final FacebookClient facebookClient;
    private final UserSession userSession;

    public ProfilePresenter(UserSession userSession, FacebookClient facebookClient) {
        this.userSession = userSession;
        this.facebookClient = facebookClient;
    }

    public void init() {
        FacebookPublicProfile userProfile = userSession.getFacebookUserProfile();
        if (userProfile != null) {
            if (isViewAttached()) {


                if (!TextUtils.isEmpty(userProfile.getCover().getSource())) {
                    getView().updateCover(userProfile.getCover().getSource());
                }

                if (!TextUtils.isEmpty(userProfile.getGender())) {
                    getView().updateGender(userProfile.getGender());
                }

                if (!TextUtils.isEmpty(userProfile.getEmail())) {
                    getView().updateEmail(userProfile.getEmail());
                }

                if (!TextUtils.isEmpty(userProfile.getBirthday())) {
                    getView().updateBirthday(userProfile.getBirthday());
                }

                if(!TextUtils.isEmpty(userProfile.getQuotes())){
                    getView().updateQuote(userProfile.getQuotes());
                }

                if(!TextUtils.isEmpty(userProfile.getName())){
                    getView().updateName(userProfile.getName());
                }
            }

        }
    }
}
