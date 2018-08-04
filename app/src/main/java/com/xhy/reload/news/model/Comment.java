package com.xhy.reload.news.model;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Comment extends LitePalSupport{

    private String comAuthor;
    private String comArticleId;
    private Date comPublishTime;
    private String comPublishTimeStr;
    private String comContent;
    private int comAuthorImg;
    private String comAaddress;
    private String comPhone;
    private int comZanNum;

    public String getComPublishTimeStr() {
        return comPublishTimeStr;
    }

    public void setComPublishTimeStr(String comPublishTimeStr) {
        this.comPublishTimeStr = comPublishTimeStr;
    }

    public String getComAuthor() {
        return comAuthor;
    }

    public void setComAuthor(String comAuthor) {
        this.comAuthor = comAuthor;
    }

    public String getComArticleId() {
        return comArticleId;
    }

    public void setComArticleId(String comArticleId) {
        this.comArticleId = comArticleId;
    }

    public Date getComPublishTime() {
        return comPublishTime;
    }

    public void setComPublishTime(Date comPublishTime) {
        this.comPublishTime = comPublishTime;
    }

    public String getComContent() {
        return comContent;
    }

    public void setComContent(String comContent) {
        this.comContent = comContent;
    }

    public int getComAuthorImg() {
        return comAuthorImg;
    }

    public void setComAuthorImg(int comAuthorImg) {
        this.comAuthorImg = comAuthorImg;
    }

    public String getComAaddress() {
        return comAaddress;
    }

    public void setComAaddress(String comAaddress) {
        this.comAaddress = comAaddress;
    }

    public String getComPhone() {
        return comPhone;
    }

    public void setComPhone(String comPhone) {
        this.comPhone = comPhone;
    }

    public int getComZanNum() {
        return comZanNum;
    }

    public void setComZanNum(int comZanNum) {
        this.comZanNum = comZanNum;
    }
}
