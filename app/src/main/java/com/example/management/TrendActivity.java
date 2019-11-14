package com.example.management;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class TrendActivity extends AppCompatActivity {

    private TextView textView_trend;
    private TextView textView_relate;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ArrayList<TrendRelateObject1> list = new ArrayList();
    private ArrayList<TrendRelateObject> r_list = new ArrayList();

    private TrendRelateAdapter adapterA;
    private TrendRelateAdapter1 adapter;
    private String trend_news_link, relate_news_link;
    private String trend;
    private String relate;
    private String[] relate_trend = {"", "", "", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);

        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        textView_trend = (TextView) findViewById(R.id.textView_trend);
        textView_relate = (TextView) findViewById(R.id.relate);
        Intent intent = getIntent();
        trend = intent.getStringExtra("trend");
        relate = intent.getStringExtra("relate");
        relate_trend[0] = intent.getStringExtra("relate1");
        relate_trend[1] = intent.getStringExtra("relate2");
        relate_trend[2] = intent.getStringExtra("relate3");
        relate_trend[3] = intent.getStringExtra("relate4");

        trend_news_link = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=" + trend;
        textView_trend.setText(trend);
        textView_relate.setText(relate);
        new Description().execute();
    }

    private class Description extends AsyncTask<Void, Void, Void> {

        //진행바표시
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //진행다일로그 시작
            progressDialog = new ProgressDialog(TrendActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            int a = 0, b = 0;
            String trendA, img_link = "", news_link = "", news = "";

            try {
                Document doc = Jsoup.connect(trend_news_link).get(); //트랜드
                Elements mElementDataSize = doc.select("ul.type01").select("li");
                for (Element elem : mElementDataSize) {
                    news = elem.select("dl dt a").text();
                    String who = elem.select("span._sp_each_source").text();
                    img_link = elem.select("div.thumb a img").attr("src");
                    news_link = elem.select("div.thumb a").attr("href");

                    if (news.equals("")) continue;
                    list.add(new TrendRelateObject1(news, who, img_link, news_link));
                }

                for (a = 0; a < 4; a++) {
                    relate_news_link = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=" + relate_trend[a];
                    Document docA = Jsoup.connect(relate_news_link).get();
                    Elements mElementDataSizeA = docA.select("ul.type01").select("li");
                    for (Element elemA : mElementDataSizeA) {
                        String newsA = elemA.select("dl dt a").text();
                        String whoA = elemA.select("span._sp_each_source").text();
                        String img_linkA = elemA.select("div.thumb a img").attr("src");
                        String news_linkA = elemA.select("div.thumb a").attr("href");
                        r_list.add(new TrendRelateObject(relate_trend[a], newsA, whoA, img_linkA, news_linkA));
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            adapter = new TrendRelateAdapter1(TrendActivity.this, list);
            adapterA = new TrendRelateAdapter(TrendActivity.this, r_list);

            recyclerView2.setAdapter(adapterA);
            recyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView2.setLayoutManager(layoutManager2);
            recyclerView.setLayoutManager(layoutManager);
            progressDialog.dismiss();
        }
    }
}