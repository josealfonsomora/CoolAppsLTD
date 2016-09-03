package com.josealfonsomora.coolappsfacebook.facebookAPI;

import com.google.gson.annotations.SerializedName;

public class FacebookData {
    @SerializedName("is_silhouette")
    boolean isSiluette;

    String url;

    public boolean isSiluette() {
        return isSiluette;
    }

    public void setSiluette(boolean siluette) {
        isSiluette = siluette;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
