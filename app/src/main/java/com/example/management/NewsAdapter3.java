package com.example.management;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsAdapter3 extends RecyclerView.Adapter<NewsAdapter3.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<NewsObject> mList;
    private Context mContext;

    public NewsAdapter3(Context context, ArrayList<NewsObject> list) {
        this.mList = list;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_headline, textView_who;
        private ImageView imageView_news;

        public ViewHolder(View newsView) {
            super(newsView);

            textView_headline = (TextView) newsView.findViewById(R.id.textView_headline);
            textView_who = (TextView) newsView.findViewById(R.id.textView_who);
            imageView_news = (ImageView) newsView.findViewById(R.id.imageView_news);
        }
    }


    @NonNull
    @Override
    public NewsAdapter3.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_news_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter3.ViewHolder holder, final int position) {
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

        Glide.with(holder.itemView).load(mList.get(position).getImg())
                .into(holder.imageView_news);


    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}