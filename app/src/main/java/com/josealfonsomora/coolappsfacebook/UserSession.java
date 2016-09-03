package com.josealfonsomora.coolappsfacebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressLint("CommitPrefEdits")
public class UserSession {

    private final SharedPreferences session;
    private final SharedPreferences.Editor editor;

    private final String EMAIL = "EMAIL";
    private final String USER_NAME = "USER_NAME";
    private final String FACEBOOK_USER_ID = "FACEBOOK_USER_ID";
    private final String FACEBOOK_ACCESS_TOKEN = "FACEBOOK_ACCESS_TOKEN";

    public UserSession(Context context) {
        session = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        editor = session.edit();
    }

    public void clear() {
        editor.clear().commit();
    }

    public String getEmail() {
        return session.getString(EMAIL, "");
    }

    public void setEmail(String email) {
        editor.putString(EMAIL, email).commit();
    }

    public String getFacebookUserId() {
        return session.getString(FACEBOOK_USER_ID, "");
    }

    public void setFacebookUserId(String facebookUserId) {
        editor.putString(FACEBOOK_USER_ID, facebookUserId).commit();
    }

    public String getFacebookAccessToken() {
        return session.getString(FACEBOOK_ACCESS_TOKEN, "");
    }

    public void setFacebookAccessToken(String facebookAccessToken) {
        editor.putString(FACEBOOK_ACCESS_TOKEN, facebookAccessToken).commit();
    }
}


