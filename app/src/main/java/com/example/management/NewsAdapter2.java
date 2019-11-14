package com.example.management;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

public class NewsAdapter2 extends RecyclerView.Adapter<NewsAdapter2.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<NewsObject_Sub> sList;
    private ArrayList<NewsObject_Sub> mList;
    private Context mContext;

    public NewsAdapter2(Context context, ArrayList<NewsObject_Sub> list, ArrayList<NewsObject_Sub> s_list) {
        this.mList = list;
        this.sList = s_list;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_headline, textView_who, textView_count;
        private ImageButton textView_save;
        private ImageView imageView_news;

        public ViewHolder(View newsView) {
            super(newsView);

            textView_save = (ImageButton) newsView.findViewById(R.id.textView_save);
            textView_headline = (TextView) newsView.findViewById(R.id.textView_headline);
            textView_who = (TextView) newsView.findViewById(R.id.textView_who);
            textView_count = (TextView) newsView.findViewById(R.id.textView_count);
            imageView_news = (ImageView) newsView.findViewById(R.id.imageView_news);
        }
    }

    @NonNull
    @Override
    public NewsAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_data_sub, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter2.ViewHolder holder, final int position) {

        LoginActivity loginActivity = new LoginActivity();
        final String userID = LoginActivity.ID;
        final String title = String.valueOf(mList.get(position).getHeadline());
        final String who = String.valueOf(mList.get(position).getWho());
        final String link = String.valueOf(mList.get(position).getDetail_link());
        holder.textView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setMessage("저장하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                holder.textView_save.setBackgroundResource(R.drawable.ic_favorite_black_24dp);
                                holder.textView_save.setEnabled(false);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setMessage("저장 실패.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                SaveRegisterRequest newsRegisterRequest = new SaveRegisterRequest(userID, title, who, link,responseListener);
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(newsRegisterRequest);
            }
        });



        holder.textView_headline.setText(String.valueOf(mList.get(position).getHeadline()));
        holder.textView_headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mList.get(position).getDetail_link()));
                intent.setPackage("com.android.chrome");
                mContext.startActivity(intent);
            }
        });
        holder.textView_who.setText(String.valueOf(mList.get(position).getWho()));

        holder.textView_count.setText(String.valueOf(mList.get(position).getCount()));
        Glide.with(holder.itemView).load(mList.get(position).getImg())
                .into(holder.imageView_news);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}