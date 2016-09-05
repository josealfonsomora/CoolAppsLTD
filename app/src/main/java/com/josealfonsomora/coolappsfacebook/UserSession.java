package com.josealfonsomora.coolappsfacebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.josealfonsomora.coolappsfacebook.facebookAPI.FacebookPublicProfile;

@SuppressLint("CommitPrefEdits")
public class UserSession {

    private final SharedPreferences session;
    private final SharedPreferences.Editor editor;

    private final String EMAIL = "EMAIL";
    private final String USER_NAME = "USER_NAME";
    private final String FACEBOOK_USER_ID = "FACEBOOK_USER_ID";
    private final String FACEBOOK_ACCESS_TOKEN = "FACEBOOK_ACCESS_TOKEN";
    private final String FACEBOOK_USER_NAME = "FACEBOOK_USER_NAME";
    private final String FACEBOOK_GENDER = "FACEBOOK_GENDER";
    private final String FACEBOOK_LINK = "FACEBOOK_LINK";
    private final String FACEBOOK_PICTURE_URL = "FACEBOOK_PICTURE_URL";
    private final String FACEBOOK_PUBLIC_PROFILE = "FACEBOOK_PUBLIC_PROFILE";

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

    public void setUserName(String name) {
        editor.putString(FACEBOOK_USER_NAME, name).commit();
    }

    public void setGender(String gender) {
        editor.putString(FACEBOOK_GENDER, gender).commit();
    }

    public void setLink(String link) {
        editor.putString(FACEBOOK_LINK, link).commit();
    }

    public void setPictureUrl(String pictureUrl) {
        editor.putString(FACEBOOK_PICTURE_URL, pictureUrl).commit();
    }
    public String getPictureUrl() {
        return session.getString(FACEBOOK_PICTURE_URL, "");
    }

    public void setFacebookUserProfile(FacebookPublicProfile profile) {
        Gson gson = new Gson();
        String json = gson.toJson(profile);
        editor.putString(FACEBOOK_PUBLIC_PROFILE, json).commit();
    }

    public FacebookPublicProfile getFacebookUserProfile(){
        Gson gson = new Gson();
        String json = session.getString(FACEBOOK_PUBLIC_PROFILE, "");
        try {
            FacebookPublicProfile obj = gson.fromJson(json, FacebookPublicProfile.class);
            return obj;
        }catch (Exception e){
            Log.e("UserSession",e.toString());
            return null;
        }
    }
}


