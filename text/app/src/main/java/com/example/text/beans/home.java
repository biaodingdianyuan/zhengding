package com.example.text.beans;

import java.util.ArrayList;

/**
 * Created by 刘海风 on 2016/8/1.
 */

public class home {
    private String date;
    private ArrayList<stories> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<com.example.text.beans.stories> getStories() {
        return stories;
    }

    public void setStories(ArrayList<com.example.text.beans.stories> stories) {
        this.stories = stories;
    }
}
