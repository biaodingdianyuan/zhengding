package com.example.text.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 刘海风 on 2016/8/1.
 */

public class result implements Serializable{
  private ArrayList<author> authors;
    private String now;
    private String title_hide;
    private String url;
    private String small_image;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSmall_image() {
        return small_image;
    }

    public void setSmall_image(String small_image) {
        this.small_image = small_image;
    }

    public String getTitle_hide() {
        return title_hide;
    }

    public void setTitle_hide(String title_hide) {
        this.title_hide = title_hide;
    }

    public ArrayList<author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<author> authors) {
        this.authors = authors;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }
}
