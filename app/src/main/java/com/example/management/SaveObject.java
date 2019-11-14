package com.example.management;

public class SaveObject {

    String userID;
    String title;
    String who;
    String link;

    public String getUserID() {
        return userID;
    }

    public String getLink() {
        return link;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public SaveObject(String userID, String title, String who, String link) {
        this.userID = userID;
        this.title = title;
        this.who = who;
        this.link = link;
    }
}
