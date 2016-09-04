package com.josealfonsomora.coolappsfacebook.profile;

import com.josealfonsomora.coolappsfacebook.UserSession;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookClient;
import com.josealfonsomora.coolappsfacebook.mvp.BasePresenter;

public class ProfilePresenter extends BasePresenter<ProfileView> {
    private final FacebookClient facebookClient;
    private final UserSession userSession;

    public ProfilePresenter(UserSession userSession, FacebookClient facebookClient) {
        this.userSession = userSession;
        this.facebookClient = facebookClient;
    }

    public void init() {

    }
}
