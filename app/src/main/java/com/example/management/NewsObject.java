package com.example.management;

public class NewsObject {
    private String headline; //제목
    private String who; //신문사
    private String detail_link; //링크
    private String count; // 조회수
    private String img; //이미지링크
    private String body;
    private String field;
    private String add;
    private String comment;
    private int num;

    public  int getNum(){return  num;}
    public String getField() {
        return field;
    }
    public String getComment(){return comment;
    }
    public String getAdd() {
        return add;
    }

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

    public NewsObject(String headline, String who, String detail_link, String count, String img, String body, String field, String add, String comment, int num) {
        this.headline = headline;
        this.who = who;
        this.comment = comment;
        this.detail_link = detail_link;
        this.count = count;
        this.img = img;
        this.body = body;
        this.add = add;
        this.field = field;
        this.num = num;
    }
}