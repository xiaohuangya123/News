package com.xhy.reload.news.model;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Article extends LitePalSupport {
    private int id;
    private String title;
    private String content;
    private String author;
    private String summary;
    private int titleImg;
    private Date publishTime;
    private String publicTimeStr;
    private int columnId;

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublicTimeStr() {
        return publicTimeStr;
    }

    public void setPublicTimeStr(String publicTimeStr) {
        this.publicTimeStr = publicTimeStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(int titleImg) {
        this.titleImg = titleImg;
    }

    public int getColumnId() {
        return columnId;
    }

    public void setColumnId(int columnId) {
        this.columnId = columnId;
    }
}
