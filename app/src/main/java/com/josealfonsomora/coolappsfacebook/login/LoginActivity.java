package com.josealfonsomora.coolappsfacebook.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.josealfonsomora.coolappsfacebook.App;
import com.josealfonsomora.coolappsfacebook.AppSettings;
import com.josealfonsomora.coolappsfacebook.main.MainActivity;
import com.josealfonsomora.coolappsfacebook.R;
import com.josealfonsomora.coolappsfacebook.mvp.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    @Inject
    public LoginPresenter presenter;

    private CallbackManager facebookCallbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.getApp().component().inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        facebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookLoginCallback());
    }

    @OnClick(R.id.login_button)
    public void onFacebookButtonClicked() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, AppSettings.FACEBOOK_PERMISSIONS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);

        initializeCoolBackground();
    }

    private void initializeCoolBackground() {
        // TODO
    }

    @Override
    protected void onPause() {
        presenter.detachView();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(FacebookSdk.isFacebookRequestCode(requestCode)) {
            if(resultCode == RESULT_OK){
                if(data!=null){
                    facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    public void moveToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void showFacebookError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .create().show();
    }

    @Override
    public void showMushLoginWithFacebookToContinueError() {
        Toast.makeText(LoginActivity.this, "You must log in with Facebook to continue", Toast.LENGTH_SHORT).show();
    }

    private class FacebookLoginCallback implements FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(LoginResult loginResult) {
            presenter.onFacebookCallbackSuccess(loginResult);
        }

        @Override
        public void onCancel() {
            presenter.onFacebookLoginCallbackCancel();
        }

        @Override
        public void onError(FacebookException error) {
            presenter.onFacebookLoginCallbackError(error);
        }
    }
}
