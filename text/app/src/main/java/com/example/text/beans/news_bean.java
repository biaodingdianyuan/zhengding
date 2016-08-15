package com.example.text.beans;

/**
 * Created by 刘海风 on 2016/7/28.
 */

public class news_bean {
   /* title: 标题
    link: 链接
    description: 描述*/
    private String title;
    private String link;
    private String author;
    private String description;

    public void news1(){

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void  news1(String title, String link, String description,String author){
        this.title=title;
        this.link=link;
        this.description=description;
        this.author=author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
