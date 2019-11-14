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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<NewsObject> sList;
    private ArrayList<NewsObject> mList;
    private Context mContext;

    public NewsAdapter(Context context, ArrayList<NewsObject> list, ArrayList<NewsObject> s_list) {
        this.mList = list;
        this.sList = s_list;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_headline, textView_who, textView_count, textView_field, textView_add;
        private ImageView imageView_news;

        public ViewHolder(View newsView) {
            super(newsView);

            textView_add = (TextView) newsView.findViewById(R.id.textView_add);
            textView_field = (TextView) newsView.findViewById(R.id.textView_field);
            textView_headline = (TextView) newsView.findViewById(R.id.textView_headline);
            textView_who = (TextView) newsView.findViewById(R.id.textView_who);
            textView_count = (TextView) newsView.findViewById(R.id.textView_count);
            imageView_news = (ImageView) newsView.findViewById(R.id.imageView_news);
        }
    }


    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsAdapter.ViewHolder holder, final int position) {
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
        holder.textView_add.setText(String.valueOf(mList.get(position).getAdd()));


        holder.textView_field.setText(String.valueOf(mList.get(position).getField()));
        if (mList.get(position).getField().equals("정치")) {
            holder.textView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, News_1.class);
                    mContext.startActivity(intent);
                }
            });
        }
        if (mList.get(position).getField().equals("경제")) {
            holder.textView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, News_2.class);
                    mContext.startActivity(intent);

                }
            });
        }
        if (mList.get(position).getField().equals("사회")) {
            holder.textView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, News_3.class);
                    mContext.startActivity(intent);

                }
            });
        }
        if (mList.get(position).getField().equals("생활/문화")) {
            holder.textView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, News_4.class);
                    mContext.startActivity(intent);

                }
            });
        }
        if (mList.get(position).getField().equals("세계")) {
            holder.textView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, News_5.class);
                    mContext.startActivity(intent);

                }
            });
        }
        if (mList.get(position).getField().equals("IT/과학")) {
            holder.textView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, News_6.class);
                    mContext.startActivity(intent);

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}