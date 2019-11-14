package com.example.management;


class TrendRelateObject1 {

    private String news; //제목

    public String getNews() {
        return news;
    }

    public String getWho() {
        return who;
    }

    public String getImg() {
        return img;
    }

    public String getNewslink() {
        return newslink;
    }

    public TrendRelateObject1(String news, String who, String img, String newslink) {
        this.news = news;
        this.who = who;
        this.img = img;
        this.newslink = newslink;
    }

    private String who; //신문사
    private String img; //이미지링크
    private String newslink; //뉴스링크
}
