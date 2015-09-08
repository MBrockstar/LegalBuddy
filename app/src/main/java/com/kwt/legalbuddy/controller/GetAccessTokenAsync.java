package com.kwt.legalbuddy.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.kwt.legalbuddy.webservice.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASHU on 9/8/2015.
 */
public class GetAccessTokenAsync extends AsyncTask<String, String, JSONObject> {
    private Context context;
    private Handler mHandler;
    private String URL;
    private JSONParser jsonParser = new JSONParser();

    public GetAccessTokenAsync(Context context, Handler handler, String url) {

        this.context = context;
        this.mHandler = handler;
        this.URL = url;
    }


    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject json = null;

        try {
            Toast.makeText(context, "Legal Buddy Sexy"+URL, Toast.LENGTH_LONG).show();
            json = jsonParser.makeHttpRequest(URL, "GET",null);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return json;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        try {
            if (result != null) {
                Message msg = new Message();
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
