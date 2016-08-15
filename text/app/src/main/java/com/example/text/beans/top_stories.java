package com.example.text.beans;

import java.util.Date;

/**
 * Created by 刘海风 on 2016/8/1.
 */

public class top_stories {



    private String images;
    private String type;
    private String id;
    private String ga_prefix;
    private  String title;




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getEype() {
        return type;
    }

    public void setEype(String eype) {
        this.type = eype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }
}
