package com.dealfaro.luca.backandforthstudio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    static final public String MYPREFS = "myprefs";
    static final public String PREF_STRING_1 = "string_1";

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
        SharedPreferences settings = getSharedPreferences(MainActivity.MYPREFS, 0);
        String myText = settings.getString(MainActivity.PREF_STRING_1, "");
        EditText edv = (EditText) findViewById(R.id.editText1);
        edv.setText(myText);
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        if (appInfo.sharedString != null) {
            edv2.setText(appInfo.sharedString[1]);
        }
    }

    public void goto_second(View V) {
        EditText edv1 = (EditText) findViewById(R.id.editText1);
        String text1 = edv1.getText().toString();
        appInfo.setColor(text1, 0);
        // The second string we store it in the singleton class.
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        String text2 = edv2.getText().toString();
        appInfo.setColor(text2, 1);

        // Go to second activity
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void goto_third(View V) {
        EditText edv1 = (EditText) findViewById(R.id.editText1);
        String text1 = edv1.getText().toString();
        appInfo.setColor(text1, 0);
//         The second string we store it in the singleton class.
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        String text2 = edv2.getText().toString();
        appInfo.setColor(text2, 1);

        // Go to second activity
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

}
