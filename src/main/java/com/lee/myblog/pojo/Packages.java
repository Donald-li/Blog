package com.lee.myblog.pojo;


public class Packages {
    private int pid;
    private String pname;
    private int uid;

    public Packages() {
    }

    public Packages(int pid, String pname, int uid) {
        this.pid = pid;
        this.pname = pname;
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Packages(String pname) {
        this.pname = pname;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
