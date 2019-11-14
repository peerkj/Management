package com.example.management;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoticeActivity extends AppCompatActivity {

    private boolean validate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        final EditText noticeText = (EditText) findViewById(R.id.noticeText);
        final EditText nameText = (EditText) findViewById(R.id.nameText);
        final EditText dateText = (EditText) findViewById(R.id.dateText);



    Button registerButton = (Button)findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noticeContent = noticeText.getText().toString();
                String noticeNAME = nameText.getText().toString();
                String noticeDATE = dateText.getText().toString();

                if(noticeContent.equals("")||noticeNAME.equals("")||noticeDATE.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                    builder.setMessage("빈칸없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create()
                            .show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                                builder.setMessage("공지 성공.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();

                                finish();
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NoticeActivity.this);
                                builder.setMessage("공지 실패.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                NoticeRegisterRequest NoticeRegisterRequest = new NoticeRegisterRequest(noticeContent, noticeNAME, noticeDATE, responseListener);
                RequestQueue queue = Volley.newRequestQueue(NoticeActivity.this);
                queue.add(NoticeRegisterRequest);
            }
        });
    }
}
