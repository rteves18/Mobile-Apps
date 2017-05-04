package com.dealfaro.luca.androidhomephone;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
//import static com.dealfaro.luca.androidhomephone.R.id.detailView;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "androidhomephone";

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // We need the "final" keyword here to guarantee that the
        // value does not change, as it is used in the callbacks.

//        getList();
//        sendMsg("Anna", "Clara", "abracadabra");
//        getMessages("abracadabra");
    }

    public void http_get(View view){
        getMessages("abracadabra");
    }

    public void http_post(View view){
        sendMsg("abracadabra");
    }

    private void sendMsg(final String msg) {

        final TextView detailView = (TextView) findViewById(R.id.detailView1);
        StringRequest sr = new StringRequest(Request.Method.POST,
                //"https://luca-ucsc-teaching-backend.appspot.com/api_w_ndb/send_msg",
                "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_post",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(LOG_TAG, "sendMsg Got:" + response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String value = obj.getString("result");
                    detailView.setText(value);
                } catch (JSONException e){
                   throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("token", msg);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

    }


    private void getMessages(final String msg) {

        // Instantiate the RequestQueue.
        final TextView detailView = (TextView) findViewById(R.id.detailView1);
        //String url = "https://luca-ucsc-teaching-backend.appspot.com/api_w_ndb/get_msg_for_me";
//        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_get";
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw4/get_news_sites";
        String my_url = url + "?token=" + URLEncoder.encode(msg);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "getMessages Received: " + response.toString());
                        try {
                            String value = response.getString("result");
                            detailView.setText(value);
                        } catch (JSONException e){
                            throw new RuntimeException(e);
                        }
                        // Ok, let's disassemble a bit the json object.
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });

        // In some cases, we don't want to cache the request.
        // jsObjRequest.setShouldCache(false);

        queue.add(jsObjRequest);
    }



    private void getList() {
//        final TextView mTextView = (TextView) findViewById(R.id.my_text);
//        final TextView detailView = (TextView) findViewById(R.id.detailView);

        // Instantiate the RequestQueue.
        String url = "https://luca-ucsc-teaching-backend.appspot.com/api/get_list";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        mTextView.setText("Response json: " + response.toString());
                        Log.d(LOG_TAG, "getList Received: " + response.toString());
                        // Ok, let's disassemble a bit the json object.
                        try {
                            JSONArray receivedList = response.getJSONArray("mylist");
                            String allTogether = "(";
                            for (int i = 0; i < receivedList.length(); i++) {
                                allTogether += receivedList.getString(i) + ";";
                            }
                            allTogether += ")";
//                            detailView.setText(allTogether);
                        } catch (Exception e) {
//                            mTextView.setText("Aaauuugh, received bad json: " + e.getStackTrace());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });

        // In some cases, we don't want to cache the request.
        // jsObjRequest.setShouldCache(false);

        queue.add(jsObjRequest);
    }

}
