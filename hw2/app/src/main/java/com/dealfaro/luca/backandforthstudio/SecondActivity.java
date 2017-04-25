package com.dealfaro.luca.backandforthstudio;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

    static final public String MYPREFS = "myprefs";
    static final public String PREF_STRING_1 = "string_1";

    AppInfo appInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        appInfo = AppInfo.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Writes the string from main activity.
        SharedPreferences settings = getSharedPreferences(MYPREFS, 0);
        String myText = settings.getString(MainActivity.PREF_STRING_1, "");
        TextView tv = (TextView) findViewById(R.id.textView2);
        tv.setText(myText);

        // and the one from the singleton object
        TextView tv2 = (TextView) findViewById(R.id.textView3);
        tv2.setText(appInfo.sharedString);
        TextView tv3 = (TextView) findViewById(R.id.textView4);
        tv3.setText(appInfo.sharedString);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void goto_first(View V) {
//        // Grab the text, and store it in a preference.
//        EditText edv = (EditText) findViewById(R.id.editText2);
//        String text2 = edv.getText().toString();
//        SharedPreferences settings = getSharedPreferences(MYPREFS, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString(PREF_STRING_1, text2);
//        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }

    public void goto_third(View V) {
//        // Grab the text, and store it in a preference.
//        EditText edv = (EditText) findViewById(R.id.editText2);
//        String text2 = edv.getText().toString();
//        SharedPreferences settings = getSharedPreferences(MYPREFS, 0);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString(PREF_STRING_1, text2);
//        editor.commit();


        Intent intent = new Intent(this, ThirdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }
}
