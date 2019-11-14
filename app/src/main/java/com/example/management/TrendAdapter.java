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

public class TrendAdapter extends RecyclerView.Adapter<TrendAdapter.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<TrendObject> mList;
    private Context mContext;

    public TrendAdapter(Context context, ArrayList<TrendObject> list) {
        this.mList = list;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_trend, textView_relate, textView_news, textView_trendadd;
        private ImageView imageView_news;
        public ViewHolder(View trendView) {
            super(trendView);
            textView_trendadd = (TextView)trendView.findViewById(R.id.textView_trendadd);
            textView_trend = (TextView)trendView.findViewById(R.id.textView_trend);
            textView_news = (TextView)trendView.findViewById(R.id.textView_news);
            textView_relate = (TextView)trendView.findViewById(R.id.textView_relate);
            imageView_news = (ImageView)trendView.findViewById(R.id.imageView_news);
        }
    }

    @NonNull
    @Override
    public TrendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_maindata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrendAdapter.ViewHolder holder, final int position) {

        holder.textView_trend.setText(String.valueOf(mList.get(position).getTrend()));
        holder.textView_trend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(mList.get(position).getDetail_link()));
                mContext.startActivity(intent);
            }
        });
        holder.textView_relate.setText(String.valueOf(mList.get(position).getRelate()));
        holder.textView_news.setText(String.valueOf(mList.get(position).getNews()));
        holder.textView_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(mList.get(position).getNewslink()));
                mContext.startActivity(intent);
            }
        });
        Glide.with(holder.itemView).load(mList.get(position).getImg())
                .into(holder.imageView_news);
        holder.textView_trendadd.setText(String.valueOf(mList.get(position).getAdd()));
        holder.textView_trendadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,TrendActivity.class);
                intent.putExtra("relate",String.valueOf(mList.get(position).getRelate()));
                intent.putExtra("trend",String.valueOf(mList.get(position).getTrend()));
                intent.putExtra("relate1",String.valueOf(mList.get(position).getRelate1()));
                intent.putExtra("relate2",String.valueOf(mList.get(position).getRelate2()));
                intent.putExtra("relate3",String.valueOf(mList.get(position).getRelate3()));
                intent.putExtra("relate4",String.valueOf(mList.get(position).getRelate4()));

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}