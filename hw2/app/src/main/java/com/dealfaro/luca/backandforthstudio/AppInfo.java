package com.dealfaro.luca.backandforthstudio;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Arrays;

/**
 * Created by luca on 18/1/2016.
 */
public class AppInfo {

    private static AppInfo instance = null;
    private static final String COLOR_NAME = "color2";
    private String[] paco = new String[3];

    protected AppInfo() {
        // Exists only to defeat instantiation.
    }

    // Here are some values we want to keep global.
    public String[] sharedString = new String[3];

    private Context my_context;

    public static AppInfo getInstance(Context context) {
        if(instance == null) {
            instance = new AppInfo();
            instance.my_context = context;
            SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
            instance.sharedString[0] = settings.getString(COLOR_NAME, null);
        }
        return instance;
    }

    public void setColor(String c, int x) {
        paco[x] = c;
        instance.sharedString[x] = paco[x];
        Log.d("this is my array", "arr: " + Arrays.toString(paco));
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME, paco[x]);
        editor.commit();
    }

}
