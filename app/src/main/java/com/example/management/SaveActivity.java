package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SaveActivity extends AppCompatActivity {

    private ListView listView;
    private SaveListAdapter adapter;
    private List<SaveObject> userList;
    private List<SaveObject> saveList;
    LoginActivity loginActivity = new LoginActivity();
    String ID = LoginActivity.ID;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.listView);
        userList = new ArrayList<SaveObject>();
        saveList = new ArrayList<SaveObject>();
        EditText search = (EditText) findViewById(R.id.search);

        adapter = new SaveListAdapter(SaveActivity.this, saveList, this, userList);

        listView.setAdapter(adapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchNews(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        try {
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("saveList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String userID, title, who, link;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                userID = object.getString("userID");
                title = object.getString("title");
                who = object.getString("who");
                link = object.getString("link");
                if (ID.equals(userID)) {
                    SaveObject saveObject = new SaveObject(userID, title, who, link);
                    saveList.add(saveObject);
                    userList.add(saveObject);
                    adapter.notifyDataSetChanged();
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchNews(String search) {
        saveList.clear();
        for (int j = 0; j < userList.size(); j++) {
            if (userList.get(j).getTitle().contains(search)) {
                saveList.add(userList.get(j));
            } else if (userList.get(j).getWho().contains(search)) {
                saveList.add(userList.get(j));
            }
        }

        adapter.notifyDataSetChanged();
    }
}
