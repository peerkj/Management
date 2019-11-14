package com.example.management;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class News_1 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<NewsObject_Sub> list = new ArrayList();
    private NewsAdapter2 adapter;
    private ArrayList<NewsObject_Sub> s_list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);

        EditText search = (EditText) findViewById(R.id.search);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchNews(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //AsyncTask 작동시킴(파싱)
        new Description().execute();
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(News_1.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                int i = 0;
                Document doc = Jsoup.connect("https://news.naver.com/main/ranking/popularDay.nhn?mid=etc&sid1=111").get(); //랭킹뉴스
                Elements mElementDataSize = doc.select("ul.ranking_category_list").select("li"); //필요한 녀석만 꼬집어서 지정
                for (Element elem : mElementDataSize) {

                    String my_link = "https://news.naver.com" + elem.select("li.ranking_category_item a").attr("href");
                    if (i == 1) {
                        Document doc1 = Jsoup.connect(my_link).get();
                        Elements mElementDataSize1 = doc1.select("div.ranking ol").select("li");
                        for (Element elem1 : mElementDataSize1) {

                            String detail_link = "https://news.naver.com" + elem1.select("div.ranking_thumb a").attr("href");
                            String count = "조회수 " + elem1.select("div.ranking_view").text();
                            String who = elem1.select("div.ranking_office").text();
                            String headline = elem1.select("div.ranking_headline a").attr("title");

                            Document doc2 = Jsoup.connect(detail_link).get();
                            String img = doc2.select("span.end_photo_org img").attr("src");
                            String body = doc2.select("div #articleBodyContents").text();
                            String comment = "";

                            list.add(new NewsObject_Sub(headline, who, detail_link, count, img, body, comment));
                            s_list.add(new NewsObject_Sub(headline, who, detail_link, count, img, body, comment));
                        }
                    }
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //ArraList를 인자로 해서 어답터와 연결한다.
            adapter = new NewsAdapter2(News_1.this, list, s_list);
            recyclerView.setAdapter(adapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            progressDialog.dismiss();
        }
    }

    public void searchNews(String search) {
        list.clear();
        for (int j = 0; j < s_list.size(); j++) {
            if (s_list.get(j).getHeadline().contains(search)) {
                list.add(s_list.get(j));
            } else if (s_list.get(j).getWho().contains(search)) {
                list.add(s_list.get(j));
            } else if (s_list.get(j).getBody().contains(search)) {
                list.add(s_list.get(j));
            }
        }
        adapter.notifyDataSetChanged();
    }

}