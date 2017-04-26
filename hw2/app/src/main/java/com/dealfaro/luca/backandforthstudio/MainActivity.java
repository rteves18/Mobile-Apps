package com.dealfaro.luca.backandforthstudio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final public String MYPREFS = "myprefs";
//    static final public String PREF_STRING_1 = "string_1";

    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setText(appInfo.sharedString[1]);
        TextView tv3 = (TextView) findViewById(R.id.textView3);
        tv3.setText(appInfo.sharedString[2]);
        //insert string1
        EditText edv = (EditText) findViewById(R.id.editText1);
        if (appInfo.sharedString[0] != null) {
            edv.setText(appInfo.sharedString[0]);
        }

    }

    public void goto_first(View V) {
        EditText edv1 = (EditText) findViewById(R.id.editText1);
        String text1 = edv1.getText().toString();
        appInfo.setColor(text1, 0);

        InputMethodManager inputManager =
                (InputMethodManager) this.
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void goto_second(View V) {
        EditText edv1 = (EditText) findViewById(R.id.editText1);
        String text1 = edv1.getText().toString();
        appInfo.setColor(text1, 0);

        // Go to second activity
        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goto_third(View V) {
        EditText edv1 = (EditText) findViewById(R.id.editText1);
        String text1 = edv1.getText().toString();
        appInfo.setColor(text1, 0);

       // Go to second activity
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
