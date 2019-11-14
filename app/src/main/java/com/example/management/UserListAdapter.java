package com.example.management;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<UserObject> userList;
    private Activity parantActivity;
    private List<UserObject> saveList;

    public UserListAdapter(Context context, List<UserObject> userList, Activity parentActivity, List<UserObject> saveList) {     //매개변수 받는곳
        this.context = context;
        this.parantActivity = parentActivity;
        this.userList = userList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {

        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.user, null);
        final TextView userID = (TextView) v.findViewById(R.id.userID);
        TextView userPassword = (TextView) v.findViewById(R.id.userPassword);
        TextView userName = (TextView) v.findViewById(R.id.userName);
        TextView userMail = (TextView) v.findViewById(R.id.userMail);

        userID.setText(userList.get(i).getUserID());
        userPassword.setText(userList.get(i).getUserPassword());
        userName.setText(userList.get(i).getUserName());
        userMail.setText(userList.get(i).getUserMail());

        v.setTag(userList.get(i).getUserID());

        Button deleteButton = (Button) v.findViewById(R.id.deleteButton);
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
                                userList.remove(i);
                                for (int i = 0; i < saveList.size(); i++) {
                                    if (saveList.get(i).getUserID().equals(userID.getText().toString())) {
                                        saveList.remove(i);
                                        break;
                                    }
                                }
                                notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse2 = new JSONObject(response);
                            boolean success = jsonResponse2.getBoolean("success");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                };
                UserDeleteRequest deleteRequest = new UserDeleteRequest(userID.getText().toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(parantActivity);
                queue.add(deleteRequest);
                UserSaveDeleteRequest savedelete = new UserSaveDeleteRequest(userID.getText().toString(), responseListener);
                RequestQueue queue2 = Volley.newRequestQueue(parantActivity);
                queue2.add(savedelete);


            }
        });
        return v;
    }
}
