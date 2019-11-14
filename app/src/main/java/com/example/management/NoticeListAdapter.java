package com.example.management;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;


public class NoticeListAdapter extends BaseAdapter {


    private Context context;
    private List<NoticeObject> noticeList;
    private Activity parantActivity;


    public NoticeListAdapter(Context context, List<NoticeObject> noticeList, Activity parentActivity) {
        this.context = context;
        this.noticeList = noticeList;
        this.parantActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int i) {
        return noticeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.notice, null);
        final TextView noticeText = (TextView) v.findViewById(R.id.noticeText);
        final TextView nameText = (TextView) v.findViewById(R.id.nameText);
        TextView dateText = (TextView) v.findViewById(R.id.dateText);

        LoginActivity loginActivity = new LoginActivity();
        String ID = LoginActivity.ID;

        noticeText.setText(noticeList.get(i).getNotice());
        nameText.setText(noticeList.get(i).getName());
        dateText.setText(noticeList.get(i).getDate());

        v.setTag(noticeList.get(i).getNotice());

        TextView deleteButton = (TextView) v.findViewById(R.id.deleteButton);
        if (!ID.equals("admin")) {
            deleteButton.setVisibility(View.GONE);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                noticeList.remove(i);
                                notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                NoticeDeleteRequest noticedeleteRequest = new NoticeDeleteRequest(noticeText.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parantActivity);
                queue.add(noticedeleteRequest);
            }
        });
        return v;
    }
}
