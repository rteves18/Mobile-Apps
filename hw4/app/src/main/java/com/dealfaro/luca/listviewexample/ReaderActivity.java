package com.dealfaro.luca.listviewexample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReaderActivity extends AppCompatActivity {

    static final public String MYPREFS = "myprefs";
    static final public String PREF_URL = "restore_url";
    static final public String WEBPAGE_NOTHING = "about:blank";
    static final public String MY_WEBPAGE = "https://www.google.com/";
    static final public String LOG_TAG = "webview_example";
    String news_url = "https://www.google.com/";

    WebView myWebView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        myWebView = (WebView) findViewById(R.id.webView1);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            finish(); // No idea what else to do
        } else {
            news_url = extras.getString("URL");
        }
        myWebView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Binds the Javascript interface
        myWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        myWebView.loadUrl(news_url);
        myWebView.loadUrl("javascript:alert(\"Hello\")");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().contains(news_url)) {
                // This is my web site, so do not override; let my WebView load the page
                return false;
            }
            // Otherwise, the link is not for a page on my site,
// so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    @Override
    public void onBackPressed(){
        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            finish();
        }
    }



    public void Search(View view) {
        EditText search = (EditText) (findViewById(R.id.editText));
        String searching = search.getText().toString();
        InputMethodManager inputManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
            myWebView.loadUrl("https://www.google.com/#q=" + searching);
    }


    public class JavaScriptInterface {
        Context mContext; // Having the context is useful for lots of things,
        // like accessing preferences.

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }



        @JavascriptInterface
        public void myFunction(String args) {
            final String myArgs = args;
            Log.i(LOG_TAG, "I am in the javascript call.");
            runOnUiThread(new Runnable() {
                public void run() {
                    Button v = (Button) findViewById(R.id.button1);
                    v.setText(myArgs);
                }
            });

        }

    }


    @Override
    public void onPause() {

        Method pause = null;
        try {
            pause = WebView.class.getMethod("onPause");
        } catch (SecurityException e) {
            // Nothing
        } catch (NoSuchMethodException e) {
            // Nothing
        }
        if (pause != null) {
            try {
                pause.invoke(myWebView);
            } catch (InvocationTargetException e) {
            } catch (IllegalAccessException e) {
            }
        } else {
            // No such method.  Stores the current URL.
            String suspendUrl = myWebView.getUrl();
            SharedPreferences settings = getSharedPreferences(ReaderActivity.MYPREFS, 0);
            SharedPreferences.Editor ed = settings.edit();
            ed.putString(PREF_URL, suspendUrl);
            ed.commit();
            // And loads a URL without any processing.
            myWebView.clearView();
            myWebView.loadUrl(WEBPAGE_NOTHING);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If we stored a pause URL, reload it.
        SharedPreferences settings = getSharedPreferences(ReaderActivity.MYPREFS, 0);
        String suspendUrl = settings.getString(PREF_URL, null);
        if (suspendUrl != null) {
            myWebView.loadUrl(suspendUrl);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

