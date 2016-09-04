package com.josealfonsomora.coolappsfacebook.facebookAPI;

import java.util.List;
/*
 * This object is common for books, movies and music.
 */
public class FacebookItemPage {
    public String name;
    public String id;
    public List<Data> data;
    public Paging paging;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

}
