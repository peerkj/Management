package com.example.management;

public class UserObject {

    String userID;
    String userPassword;
    String userName;
    String userMail;

    public UserObject(String userID, String userPassword, String userName, String userMail) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userMail = userMail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
}
