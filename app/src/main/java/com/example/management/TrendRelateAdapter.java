package com.example.management;

import android.app.AlertDialog;
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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.ArrayList;

public class TrendRelateAdapter extends RecyclerView.Adapter<TrendRelateAdapter.ViewHolder> {

    //데이터 배열 선언
    private ArrayList<TrendRelateObject> mList;
    private Context mContext;

    public TrendRelateAdapter(Context context, ArrayList<TrendRelateObject> list) {
        this.mList = list;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_who, textView_relate, textView_headline;
        private ImageView imageView_news;
        private ImageButton textView_save;
        public ViewHolder(View trendView) {
            super(trendView);

            textView_headline = (TextView)trendView.findViewById(R.id.textView_headline);
            textView_relate = (TextView)trendView.findViewById(R.id.textView_relate);
            textView_who = (TextView)trendView.findViewById(R.id.textView_who);
            imageView_news = (ImageView)trendView.findViewById(R.id.imageView_news);

            textView_save = (ImageButton) trendView.findViewById(R.id.textView_save);
        }
    }

    @NonNull
    @Override
    public TrendRelateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trend_relate_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrendRelateAdapter.ViewHolder holder, final int position) {
        LoginActivity loginActivity = new LoginActivity();
        final String userID = LoginActivity.ID;
        final String link = String.valueOf(mList.get(position).getNewslink());
        final String headline = String.valueOf(mList.get(position).getNews());
        final String who = String.valueOf(mList.get(position).getWho());
        final String relate = String.valueOf(mList.get(position).getTrend());
        holder.textView_relate.setText(String.valueOf(mList.get(position).getTrend()));
        holder.textView_headline.setText(String.valueOf(mList.get(position).getNews()));
        holder.textView_headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(mList.get(position).getNewslink()));
                mContext.startActivity(intent);
            }
        });
        holder.textView_who.setText(String.valueOf(mList.get(position).getWho()));
        Glide.with(holder.itemView).load(mList.get(position).getImg())
                .into(holder.imageView_news);
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
                SaveRegisterRequest newsRegisterRequest = new SaveRegisterRequest(userID, headline, who, link,responseListener);
                RequestQueue queue = Volley.newRequestQueue(mContext);
                queue.add(newsRegisterRequest);
            }
        });
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
}