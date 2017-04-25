package com.dealfaro.luca.backandforthstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

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

        // and the one from the singleton object
        TextView tv2 = (TextView) findViewById(R.id.textView1);
        tv2.setText(appInfo.sharedString[0]);
        TextView tv3 = (TextView) findViewById(R.id.textView3);
        tv3.setText(appInfo.sharedString[2]);

        EditText edv = (EditText) findViewById(R.id.editText2);
        if (appInfo.sharedString != null) {
            edv.setText(appInfo.sharedString[1]);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void goto_second(View V) {
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        String text2 = edv2.getText().toString();
        appInfo.setColor(text2, 1);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }

    public void goto_first(View V) {
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        String text2 = edv2.getText().toString();
        appInfo.setColor(text2, 1);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }

    public void goto_third(View V) {
        EditText edv2 = (EditText) findViewById(R.id.editText2);
        String text2 = edv2.getText().toString();
        appInfo.setColor(text2, 1);

        Intent intent = new Intent(this, ThirdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // finish();
    }
}
