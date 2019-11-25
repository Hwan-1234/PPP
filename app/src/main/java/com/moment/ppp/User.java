package com.moment.ppp;

public class User {
    String userName;
    String userNum;
    String profileMsg;

    public User() {
    }

    public User(String userName, String userNum, String profileMsg) {
        this.userName = userName;
        this.userNum = userNum;
        this.profileMsg = profileMsg;
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





    public String getProfileMsg() {
        return profileMsg;
    }

    public void setProfileMsg(String profileMsg) {
        this.profileMsg = profileMsg;
    }
}
