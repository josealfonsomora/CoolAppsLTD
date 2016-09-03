package com.josealfonsomora.coolappsfacebook;

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

    // Do not instantiate this!
    private AppSettings(){

    }


}
