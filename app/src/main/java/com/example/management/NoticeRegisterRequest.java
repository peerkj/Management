package com.example.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NoticeRegisterRequest extends StringRequest {
    final static private String URL = "http://peerkj.cafe24.com/NoticeRegister.php";
    private Map<String, String> parameters;

    public NoticeRegisterRequest(String noticeText, String nameText, String dateText, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("noticeContent", noticeText);
        parameters.put("noticeNAME", nameText);
        parameters.put("noticeDATE", dateText);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
