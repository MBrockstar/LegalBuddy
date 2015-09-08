package com.kwt.legalbuddy.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.kwt.legalbuddy.controller.GetAccessTokenAsync;

import org.json.JSONObject;

/**
 * Created by ASHU on 9/8/2015.
 */
public class NdaAgreementActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(NdaAgreementActivity.this,"Legal Buddy Sexy",Toast.LENGTH_LONG).show();
        GetAccessTokenAsync aAsync = new GetAccessTokenAsync(NdaAgreementActivity.this,
                getAccessTokenHandler,"http://zciencecorporation.com/lbproject/api/nda/Ashutosh/get_token");
        aAsync.execute("http://zciencecorporation.com/lbproject/api/nda/Ashutosh/get_token");
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {

    }

    @SuppressLint("HandlerLeak")
    Handler getAccessTokenHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.obj != null) {


                Toast.makeText(NdaAgreementActivity.this, ((JSONObject) msg.obj).toString(), Toast.LENGTH_LONG).show();


            }
            else
            {
                Toast.makeText(NdaAgreementActivity.this,"Nothing",Toast.LENGTH_LONG).show();
            }
        };
    };
}
