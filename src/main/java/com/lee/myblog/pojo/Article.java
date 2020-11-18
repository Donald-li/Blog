package com.lee.myblog.pojo;

import java.util.Date;

public class Article {

    private int aid;
    private int pid;
    private int uid;
    private String content;
    private Date date;
    private String title;
    private String info;

    public Article() {
    }

    public Article(int aid, int pid, int uid, String content, Date date, String title, String info) {
        this.aid = aid;
        this.pid = pid;
        this.uid = uid;
        this.content = content;
        this.date = date;
        this.title = title;
        this.info = info;
    }

    public Article(int pid, int uid, String content, Date date, String title, String info) {
        this.pid = pid;
        this.uid = uid;
        this.content = content;
        this.date = date;
        this.title = title;
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
