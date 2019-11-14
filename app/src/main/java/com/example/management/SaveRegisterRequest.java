package com.example.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SaveRegisterRequest extends StringRequest {
    final static private String URL = "http://peerkj.cafe24.com/NewsSave.php";
    private Map<String, String> parameters;

    public SaveRegisterRequest(String userID, String title, String who, String link, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("title", title);
        parameters.put("who", who);
        parameters.put("link", link );
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
