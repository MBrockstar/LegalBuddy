package com.kwt.legalbuddy.controller;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.kwt.legalbuddy.webservice.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class SubmitRequestAsync  extends AsyncTask<String, String, JSONObject>{

    private Context context;
    private Handler mHandler;
    private String URL;
    private JSONParser jsonParser = new JSONParser();


    public SubmitRequestAsync(Context context, Handler handler, String url) {

        this.context = context;
        this.mHandler = handler;
        this.URL = "http://zciencecorporation.com/";
    }



    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject json = null;

        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("_token", params[0]));


        try {
            json = jsonParser.makeHttpRequest(URL, "POST", postParameters);
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
