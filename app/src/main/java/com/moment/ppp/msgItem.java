package com.moment.ppp;

public class msgItem {
    String name,msg,time;
    String profileUrl;

    public msgItem() {
    }

    public msgItem(String name, String message) {
    }

    public msgItem(String name, String msg, String time, String profileImg) {
        this.name = name;
        this.msg = msg;
        this.time = time;
        this.profileUrl = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
