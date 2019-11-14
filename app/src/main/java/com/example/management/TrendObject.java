package com.example.management;

public class TrendObject {
    private String trend; //검색어
    private String detail_link; // 검색어 링크
    private String relate; // 관련검색어
    private String news; //제목
    private String img; //이미지링크
    private String newslink; //뉴스링크
    private String add;
    private String relate1;
    private String relate2;
    private String relate3;
    private String relate4;

    public String getRelate1() {
        return relate1;
    }

    public String getRelate2() {
        return relate2;
    }

    public String getRelate3() {
        return relate3;
    }

    public String getRelate4() {
        return relate4;
    }

    public String getTrend() {
        return trend;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public String getAdd() {
        return add;
    }

    public String getRelate() {
        return relate;
    }

    public String getNews() {
        return news;
    }

    public String getImg() {
        return img;
    }

    public String getNewslink() {
        return newslink;
    }

    public TrendObject(String trend, String detail_link, String relate, String news, String img, String newslink, String add, String relate1, String relate2, String relate3, String relate4) {
        this.trend = trend;
        this.detail_link = detail_link;
        this.relate = relate;
        this.news = news;
        this.img = img;
        this.newslink = newslink;
        this.add = add;
        this.relate1=relate1;
        this.relate2=relate2;
        this.relate3=relate3;
        this.relate4=relate4;
    }
}
