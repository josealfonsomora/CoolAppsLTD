package com.josealfonsomora.coolappsfacebook.login;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.josealfonsomora.coolappsfacebook.UserSession;
import com.josealfonsomora.coolappsfacebook.mvp.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginView> {

    private final UserSession userSession;

    public LoginPresenter(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    public void attachView(@NonNull LoginView view) {
        super.attachView(view);
        if(!TextUtils.isEmpty(userSession.getFacebookUserId())){
            view.moveToMain();
        }
    }

    public void onFacebookLoginCallbackCancel() {
        if(isViewAttached()){
            getView().showMushLoginWithFacebookToContinueError();
        }
    }

    public void onFacebookLoginCallbackError(FacebookException error) {
        if(isViewAttached()){
            getView().showFacebookError(error.getMessage());
        }
    }

    public void onFacebookCallbackSuccess(LoginResult loginResult) {
        if (loginResult.getAccessToken() != null) {
            userSession.setFacebookUserId(loginResult.getAccessToken().getUserId());
            userSession.setFacebookAccessToken(loginResult.getAccessToken().getToken());
            if (isViewAttached()){
                getView().moveToMain();
            }
        }
    }
}
