package com.example.management;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;


public class SaveListAdapter extends BaseAdapter {

    String userID = LoginActivity.ID;
    private Context context;
    private List<SaveObject> savelist,userlist;
    private Activity parantActivity;

    public SaveListAdapter(Context context, List<SaveObject> savelist, Activity parentActivity,List<SaveObject> userlist) {
        this.context = context;
        this.savelist = savelist;
        this.userlist = userlist;
        this.parantActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return savelist.size();
    }

    @Override
    public Object getItem(int i) {
        return savelist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.save_data, null);

        final TextView textView_headline = (TextView) v.findViewById(R.id.textView_headline);
        final TextView textView_who = (TextView) v.findViewById(R.id.textView_who);

        textView_headline.setText(savelist.get(i).getTitle());
        textView_headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(savelist.get(i).getLink()));
                intent.setPackage("com.android.chrome");
                context.startActivity(intent);
            }
        });
        textView_who.setText(savelist.get(i).getWho());
        TextView savedelete = (TextView) v.findViewById(R.id.savedelete);
        savedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                savelist.remove(i);
                                notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                SaveDeleteRequest deleteRequest = new SaveDeleteRequest(userID, textView_headline.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parantActivity);
                queue.add(deleteRequest);
            }
        });

        v.setTag(savelist.get(i).getUserID());
        return v;
    }
}
