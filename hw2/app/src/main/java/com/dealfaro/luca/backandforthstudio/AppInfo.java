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
    private static final String[] COLOR_NAME = new String[3];
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

            for (int i = 0; i < 3; i++) {
                SharedPreferences settings = context.getSharedPreferences(MainActivity.MYPREFS, 0);
                instance.sharedString[i] = settings.getString(COLOR_NAME[i], null);
            }
        }
        return instance;
    }

    public void setColor(String c, int x) {
        paco[x] = c;
        instance.sharedString[x] = paco[x];
        Log.d("paco", "arr: " + Arrays.toString(paco));
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(COLOR_NAME[x], c);
//        editor.putString(COLOR_NAME[x], paco[x]);
//        Log.d("COLOR_NAME", "arr: " + Arrays.toString(COLOR_NAME));
        editor.commit();
    }

}
