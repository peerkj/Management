package com.example.management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<NoticeObject> noticeList;

    private RecyclerView recyclerView2;
    private ArrayList<TrendObject> trendlist = new ArrayList<>();
    private TrendAdapter trendAdapter;

    private RecyclerView recyclerView;
    private ArrayList<NewsObject> list = new ArrayList();
    private ArrayList<NewsObject> s_list = new ArrayList();
    private NewsAdapter newsadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        TextView managementButton = (TextView) findViewById(R.id.managementButton);
        TextView savelist = (TextView) findViewById(R.id.savenews);
        TextView noticeButton = (TextView) findViewById(R.id.noticeButton);

        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");

        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<NoticeObject>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList, this);
        noticeListView.setAdapter(adapter);

        if (!userID.equals("admin")) {
            noticeButton.setVisibility(View.GONE);
        }

        if (!userID.equals("admin")) {
            managementButton.setVisibility(View.GONE);
        }
        savelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask2().execute();
            }
        });
        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BackgroundTask().execute();
            }
        });
        noticeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoticeActivity.class);
                startActivity(intent);
            }
        });

        new BackgroundTask3().execute();
        new BackgroundTask1().execute();
    }

    class BackgroundTask3 extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://peerkj.cafe24.com/NoticeList.php";
            super.onPreExecute();
            //진행다일로그 시작
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            int i, j, num;
            try {
                i = 0;
                org.jsoup.nodes.Document doc = Jsoup.connect("https://news.naver.com/main/ranking/popularDay.nhn?mid=etc&sid1=111").get();
                Elements mElementDataSize = doc.select("ul.ranking_category_list").select("li");
                for (Element elem : mElementDataSize) {

                    String my_link = "https://news.naver.com" + elem.select("li.ranking_category_item a").attr("href");
                    if (i != 0 && i <= 6) {
                        j = 0;
                        org.jsoup.nodes.Document doc1 = Jsoup.connect(my_link).get();
                        Elements mElementDataSize1 = doc1.select("div.ranking ol").select("li");
                        for (Element elem1 : mElementDataSize1) {

                            String add = "더보기";
                            String comment = "";
                            String detail_link = "https://news.naver.com" + elem1.select("div.ranking_thumb a").attr("href");
                            String count = "조회수 " + elem1.select("div.ranking_view").text();
                            String who = elem1.select("div.ranking_office").text();
                            String headline = elem1.select("div.ranking_headline a").attr("title");
                            String img = elem1.select("div.ranking_thumb a img").attr("src");
                            String body = "";
                            if (i == 1) {
                                num = 1;
                                String field = "정치";
                                list.add(new NewsObject(headline, who, detail_link, count, img, body, field, add, comment, num));
                            }
                            if (i == 2) {
                                num = 2;
                                String field = "경제";
                                list.add(new NewsObject(headline, who, detail_link, count, img, body, field, add, comment, num));
                            }
                            if (i == 3) {
                                num = 3;
                                String field = "사회";
                                list.add(new NewsObject(headline, who, detail_link, count, img, body, field, add, comment, num));
                            }
                            if (i == 4) {
                                num = 4;
                                String field = "생활/문화";
                                list.add(new NewsObject(headline, who, detail_link, count, img, body, field, add, comment, num));
                            }
                            if (i == 5) {
                                num = 5;
                                String field = "세계";
                                list.add(new NewsObject(headline, who, detail_link, count, img, body, field, add, comment, num));
                            }
                            if (i == 6) {
                                num = 6;
                                String field = "IT/과학";
                                list.add(new NewsObject(headline, who, detail_link, count, img, body, field, add, comment, num));
                            }
                            break;
                        }
                    }
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {

            progressDialog.dismiss();
            newsadapter = new NewsAdapter(MainActivity.this, list, s_list);
            recyclerView.setAdapter(newsadapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    NoticeObject notice = new NoticeObject(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    adapter.notifyDataSetChanged();
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class BackgroundTask2 extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {

            target = "http://peerkj.cafe24.com/SaveList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, SaveActivity.class);
            intent.putExtra("saveList", result);
            MainActivity.this.startActivity(intent);
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {

            target = "http://peerkj.cafe24.com/List.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
            intent.putExtra("userList", result);
            MainActivity.this.startActivity(intent);
        }
    }


    class BackgroundTask1 extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //진행다일로그 시작
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("잠시 기다려 주세요.");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            int a = 0, b = 0;
            int[] score = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
            int i = 0, j, k;
            String[] trend = new String[30];
            String x;
            String img_link = "", news_link = "", news = "";
            String[] relate = new String[10];
            String trendadd = "더보기";
            String[][] relate1 = new String[10][4];
            try {
                Document daum = Jsoup.connect("https://www.daum.net/").get();
                Elements mdaum = daum.select("ol[class=list_hotissue issue_row]").select("li");
                for (Element elem : mdaum) {
                    Element relem = elem.select("div").next().first();
                    trend[i] = relem.select("span.txt_issue a").text();
                    i++;
                    if (i == 10) break;
                }
                Document nate = Jsoup.connect("https://search.daum.net/nate?w=tot&DA=BFT&nil_profile=fix_similar&q=네이트+트렌드").get();
                Elements mnate = nate.select("div.coll_cont ol").select("li");
                for (Element elem : mnate) {
                    Element relem = elem.select("div").next().first();
                    trend[i] = relem.select("a").text();
                    i++;
                    if (i == 20) break;
                }
                Document naver = Jsoup.connect("https://www.naver.com").get();
                Elements mnaver = naver.select("ul.ah_l").select("li");
                for (Element elemA : mnaver) {
                    trend[i] = elemA.select("li.ah_item a span.ah_k").text();
                    i++;
                    if (i == 30) break;
                }
                for (i = 0; i < 10; i++) {
                    for (j = 10; j < 30; j++) {
                        if (score[i] > 0 && score[j] > 0 && trend[i].contains(trend[j])) {
                            score[i] += score[j];
                            score[j] = 0;
                            trend[j] = "";
                        }
                    }
                }
                for (i = 10; i < 20; i++) {
                    for (j = 20; j < 30; j++) {
                        if (score[i] > 0 && score[j] > 0 && trend[i].contains(trend[j])) {
                            score[i] += score[j];
                            score[j] = 0;
                            trend[j] = "";
                        }
                    }
                }
                for (i = 0; i < 30; i++) {
                    for (j = i + 1; j < 30; j++) {
                        if (score[i] < score[j]) {
                            x = trend[i];
                            trend[i] = trend[j];
                            trend[j] = x;
                            k = score[i];
                            score[i] = score[j];
                            score[j] = k;
                        }
                    }
                }
                for (a = 0; a < 10; a++) {
                    String detail_link_trend = "https://search.naver.com/search.naver?sm=top_hty&fbm=1&ie=utf8&query=" + trend[a];
                    Document docB = Jsoup.connect(detail_link_trend).get();
                    Elements mElementDataSize1 = docB.select("dd.lst_relate ul._related_keyword_ul").select("li");
                    relate[a] = "연관검색어 : ";
                    for (Element elemB : mElementDataSize1) {
                        if (b == 3) {
                            relate[a] = relate[a] + elemB.select("li a").text();
                        } else {
                            relate[a] = relate[a] + elemB.select("li a").text() + ", ";
                        }
                        relate1[a][b] = elemB.select("li a").text();
                        b++;
                        if (b == 4) {
                            b = 0;
                            break;
                        }
                    }
                    String newslink = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=" + trend[a];
                    Document docC = Jsoup.connect(newslink).get();
                    Elements mElementDataSizeB = docC.select("ul.type01").select("li");
                    for (Element elemB : mElementDataSizeB) {
                        news = elemB.select("dl dt a").text();
                        img_link = elemB.select("div.thumb a img").attr("src");
                        news_link = elemB.select("div.thumb a").attr("href");
                        break;
                    }
                    trendlist.add(new TrendObject(trend[a], detail_link_trend, relate[a], news, img_link, news_link, trendadd
                            , relate1[a][0], relate1[a][1], relate1[a][2], relate1[a][3]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            trendAdapter = new TrendAdapter(MainActivity.this, trendlist);
            recyclerView2.setAdapter(trendAdapter);

            RecyclerView.LayoutManager layoutManagerB = new LinearLayoutManager(getApplicationContext());
            recyclerView2.setLayoutManager(layoutManagerB);

            progressDialog.dismiss();

        }
    }
}


