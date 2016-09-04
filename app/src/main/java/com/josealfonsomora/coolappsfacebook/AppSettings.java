package com.josealfonsomora.coolappsfacebook;

import android.graphics.Color;

import java.util.Arrays;
import java.util.List;

public class AppSettings {
    public static final List<String> FACEBOOK_PERMISSIONS = Arrays.asList(
            "public_profile",
            "user_friends",
            "email",
            "user_about_me",
            "user_birthday",
            "user_education_history",
            "user_events",
            "user_games_activity",
            "user_hometown",
            "user_status",
            "user_likes",
            "user_location",
            "user_photos",
            "user_posts",
            "user_relationships",
            "user_relationship_details",
            "user_religion_politics",
            "user_tagged_places",
            "user_videos",
            "user_website"


    );
    public static String FACEBOOK_BASE_URL = "https://graph.facebook.com/v2.7/";

    public static final int[] PIECHART_COLORS = new int[]{
            rgb("#2ecc71"), rgb("#f1c40f"), rgb("#e74c3c"), rgb("#3498db")};

    public static int rgb(String hex) {
        int color = (int)Long.parseLong(hex.replace("#", ""), 16);
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color >> 0 & 255;
        return Color.rgb(r, g, b);
    }

    // Do not instantiate this!
    private AppSettings() {

    }


}
