package com.example.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NoticeDeleteRequest extends StringRequest {

    final static private String URL = "http://peerkj.cafe24.com/NoticeDelete.php";
    private Map<String, String> parameters;

    public NoticeDeleteRequest(String noticeContent, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("noticeContent",noticeContent);
    }
    @Override
    public Map <String, String> getParams(){
        return parameters;
    }
}
