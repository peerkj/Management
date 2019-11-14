package com.example.management;

public class NewsObject_Sub {
    private String headline; //제목
    private String who; //신문사
    private String detail_link; //링크
    private String count; // 조회수
    private String img; //이미지링크
    private String body;
    private String comment;

    public String getComment(){return comment;}

    public String getBody() {
        return body;
    }

    public String getHeadline() {
        return headline;
    }

    public String getWho() {
        return who;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public String getCount() {
        return count;
    }

    public String getImg() {
        return img;
    }

    public NewsObject_Sub(String headline, String who, String detail_link, String count, String img, String body, String comment) {
        this.headline = headline;
        this.comment = comment;
        this.who = who;
        this.detail_link = detail_link;
        this.count = count;
        this.img = img;
        this.body = body;
    }
}