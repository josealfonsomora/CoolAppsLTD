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


}


