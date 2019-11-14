package com.example.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SaveDeleteRequest extends StringRequest {

    final static private String URL = "http://peerkj.cafe24.com/SaveDelete.php";
    private Map<String, String> parameters;

    public SaveDeleteRequest(String userID, String title, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID",userID);
        parameters.put("title",title);
    }
    @Override
    public Map <String, String> getParams(){
        return parameters;
    }
}
