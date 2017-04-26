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
    private static final String COLOR_NAME1 = "color1";
    private static final String COLOR_NAME2 = "color2";
    private static final String COLOR_NAME3 = "color3";
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
                instance.sharedString[0] = settings.getString(COLOR_NAME1, null);
                instance.sharedString[1] = settings.getString(COLOR_NAME2, null);
                instance.sharedString[2] = settings.getString(COLOR_NAME3, null);
        }
        return instance;
    }

    public void setColor(String c, int x) {
        paco[x] = c;
        instance.sharedString[x] = paco[x];
        Log.d("paco", "arr: " + Arrays.toString(paco));
        SharedPreferences settings = my_context.getSharedPreferences(MainActivity.MYPREFS, 0);
        SharedPreferences.Editor editor = settings.edit();
        if (x ==0) editor.putString(COLOR_NAME1, c);
        if (x ==1) editor.putString(COLOR_NAME2, c);
        if (x ==2) editor.putString(COLOR_NAME3, c);
//        editor.putString(COLOR_NAME[x], paco[x]);
//        Log.d("COLOR_NAME", "arr: " + Arrays.toString(COLOR_NAME));
        editor.commit();
    }

}
