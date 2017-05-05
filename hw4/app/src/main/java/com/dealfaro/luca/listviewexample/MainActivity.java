package com.dealfaro.luca.listviewexample;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.net.Proxy;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "lv-ex";
    RequestQueue queue;

    private class ListElement {
        ListElement() {};

        ListElement(String title, String sub, String bl) {
            textLabel = title;
            textTitle = sub;

            buttonLabel = bl;
        }

        public String textLabel;
        public String textTitle;
        public String buttonLabel;
    }

    private ArrayList<ListElement> aList;

    private class MyAdapter extends ArrayAdapter<ListElement> {

        int resource;
        Context context;

        public MyAdapter(Context _context, int _resource, List<ListElement> items) {
            super(_context, _resource, items);
            resource = _resource;
            context = _context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout newView;

            ListElement w = getItem(position);

            // Inflate a new view if necessary.
            if (convertView == null) {
                newView = new LinearLayout(getContext());
                LayoutInflater vi = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi.inflate(resource,  newView, true);
            } else {
                newView = (LinearLayout) convertView;
            }

            // Fills in the view.
            TextView tv = (TextView) newView.findViewById(R.id.itemText);
            TextView tvo = (TextView) newView.findViewById(R.id.subItemText);
//            Button b = (Button) newView.findViewById(R.id.itemButton);
            tv.setText(w.textLabel);
            tvo.setText(w.textTitle);
//            b.setText(w.buttonLabel);

            // Sets a listener for the button, and a tag for the button as well.
            newView.setTag(w.textLabel);
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = "Loading " + v.getTag().toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();
                }
            });

            // Set a listener for the whole list item.
            newView.setTag(w.textLabel);
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = "Loading " + v.getTag().toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();
                }
            });

            return newView;
        }
    }

    private MyAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        aList = new ArrayList<ListElement>();
        aa = new MyAdapter(this, R.layout.list_element, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(aa);
        aa.notifyDataSetChanged();
    }



    public void clickRefresh (View v) {
        Log.i(LOG_TAG, "Requested a refresh of the list");
//        getMessages();

        final TextView detailView = (TextView) findViewById(R.id.detailVeiw1);
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw4/get_news_sites";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "getMessages Received: " + response.toString());
                        try {
                            JSONObject jsonObj = response;

                            // Getting JSON Array node
                            JSONArray contacts = jsonObj.getJSONArray("news_sites");

                            aList.clear();
                            // looping through All Contacts
                            if(contacts != null) {
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = contacts.getJSONObject(i);

                                    String url = c.getString("url");
                                    String subtitle = c.getString("subtitle");
                                    String title = c.getString("title");
//                                String address = c.getString("address");
//                                String gender = c.getString("gender");

                                    // Phone node is JSON Object
//                                JSONObject phone = c.getJSONObject("phone");
//                                String mobile = phone.getString("mobile");
//                                String home = phone.getString("home");
//                                String office = phone.getString("office");

                                    // tmp hash map for single contact
//                                HashMap<String, String> contact = new HashMap<>();
//
//                                // adding each child node to HashMap key => value
//                                contact.put("url", url);
//                                contact.put("subtitle", subtitle);
//                                contact.put("title", title);
//                                contact.put("mobile", mobile);

                                    // adding contact to contact list
//                                contactList.add(contact);
                                    if(!url.contains("null") && !title.contains("null") && !subtitle.contains("null")){
                                        aList.add(new ListElement(title, subtitle, "Delete"));
                                    }
                                }
                            }
                            aa.notifyDataSetChanged();
                            String value = response.getString("news_sites");
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

//        Random rn = new Random();
//        SecureRandomString srs = new SecureRandomString();
        // How long a list do we make?
//        int n = 4 + rn.nextInt(10);
        // Let's fill the array with n random strings.
        // NOTE: aList is associated to the array adapter aa, so
        // we cannot do here aList = new ArrayList<ListElement>() ,
        // otherwise we create another ArrayList which would not be
        // associated with aa.
        // aList = new ArrayList<ListElement>(); --- NO
//        aList.clear();
//        for (int i = 0; i < n; i++) {
//            aList.add(new ListElement(
//                srs.nextString(), "Delete"
//            ));
//        }
        // We notify the ArrayList adapter that the underlying list has changed,
        // triggering a re-rendering of the list.
//        aa.notifyDataSetChanged();
    }

}
