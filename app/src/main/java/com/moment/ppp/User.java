package com.moment.ppp;

public class User {
    String userName;
    String userNum;

    public User() {
    }

    public User(String userName, String userNum) {
        this.userName = userName;
        this.userNum = userNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}
