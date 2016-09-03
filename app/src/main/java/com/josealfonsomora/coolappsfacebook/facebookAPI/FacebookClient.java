package com.josealfonsomora.coolappsfacebook.facebookAPI;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface FacebookClient {
    @GET("{user-id}?fields=id,name,first_name,last_name,age_range,link,gender,verified")
    Observable<FacebookPublicProfile> getUserPublicProfile(
            @Path("user-id") String user,
            @Query("access_token") String accessToken);

    @GET("{user-id}/picture?redirect=0")
    Observable<FacebookPicture> getUserPictureProfile(
            @Path("user-id") String user,
            @Query("access_token") String accessToken,
            @Query("type") String type,
            @Query("height") int height,
            @Query("width") int width);
}

