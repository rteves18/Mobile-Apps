package com.dealfaro.luca.backandforthstudio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import static java.security.AccessController.getContext;

public class ThirdActivity extends AppCompatActivity {


    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // and the one from the singleton object
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setText(appInfo.sharedString[0]);
        TextView tv3 = (TextView) findViewById(R.id.textView3);
        tv3.setText(appInfo.sharedString[1]);

        EditText edv = (EditText) findViewById(R.id.editText3);
        if (appInfo.sharedString[2] != null) {
            edv.setText(appInfo.sharedString[2]);
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goto_third(View V) {
        EditText edv1 = (EditText) findViewById(R.id.editText3);
        String text3 = edv1.getText().toString();
        appInfo.setColor(text3, 2);

        InputMethodManager inputManager =
                (InputMethodManager) this.
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        // finish();
    }

    public void goto_first(View V) {
        EditText edv1 = (EditText) findViewById(R.id.editText3);
        String text3 = edv1.getText().toString();
        appInfo.setColor(text3, 2);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }

    public void goto_second(View V) {
        EditText edv1 = (EditText) findViewById(R.id.editText3);
        String text3 = edv1.getText().toString();
        appInfo.setColor(text3, 2);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }

}
