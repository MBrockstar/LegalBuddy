package com.kwt.legalbuddy.webservice;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.List;

/**
 * Created by AV6 on 9/8/2015.
 */
public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST or GET method
    public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) throws Exception {

        // Making HTTP request
        try {

            // check for request method
            if (method == "POST") {
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                httpPost.setEntity(new UrlEncodedFormEntity(params));

                // new
                HttpParams httpParameters = httpPost.getParams();
                // Set the timeout in milliseconds until a connection is
                // established.
                int timeoutConnection = 300000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 300000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

                System.out.println("http post =" + httpPost.getParams().toString());

                // new
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } else if (method == "GET") {
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                // new
                HttpParams httpParameters = httpGet.getParams();
                // Set the timeout in milliseconds until a connection is
                // established.
                int timeoutConnection = 10000;
                HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
                // Set the default socket timeout (SO_TIMEOUT)
                // in milliseconds which is the timeout for waiting for data.
                int timeoutSocket = 10000;
                HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
                // new
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }

        } catch (UnsupportedEncodingException e) {
            throw new Exception("Unsupported encoding error.");
        } catch (ClientProtocolException e) {
            throw new Exception("Client protocol error.");
        } catch (SocketTimeoutException e) {
            throw new Exception("Sorry, socket timeout.");
        } catch (ConnectTimeoutException e) {
            throw new Exception("Sorry, connection timeout.");
        } catch (IOException e) {
            throw new Exception("I/O error(May be server down).");
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            throw new Exception(e.getMessage());
        }

        // return JSON String
        return jObj;

    }

    // private void printRequestParameters(HttpServletRequest req, PrintWriter
    // out) {
    //
    // out.println("Printing All Request Parameters From HttpSerlvetRequest:");
    //
    // Enumeration<String> requestParameters = req.getParameterNames();
    // while (requestParameters.hasMoreElements()) {
    // String paramName = (String) requestParameters.nextElement();
    // out.println("Request Paramter Name: " + paramName + ", Value - " +
    // req.getParameter(paramName));
    // }
    // }

}
