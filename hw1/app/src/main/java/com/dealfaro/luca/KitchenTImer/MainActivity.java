package com.dealfaro.luca.KitchenTImer;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final private String LOG_TAG = "test2017app1";

    // Counter for the number of seconds.
    private int seconds = 0;
    private boolean clicked = false;
    private boolean updated = false;
    private Integer[] recentTime = new Integer[3];
    private int recent_count = 0;
    // Countdown timer.
    private CountDownTimer timer = null;

    // One second.  We use Mickey Mouse time.
    private static final int ONE_SECOND_IN_MILLIS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayTime();
    }

    public void onClickPlus(View v) {
        clicked = true;
        seconds += 60;
        displayTime();
    };

    public void onClickMinus(View v) {
        clicked = true;
        seconds = Math.max(0, seconds - 60);
        displayTime();
    };

    public void onClickButton1(View v) {
        if (recentTime[0] == null) {
            Context context = getApplicationContext();
            CharSequence text = "No recent timer set";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        } else {
            seconds = recentTime[0];
            TextView b1 = (TextView) findViewById(R.id.recent_1);
            int m = seconds / 60;
            int s = seconds % 60;
            b1.setText(String.format("%d:%02d", m, s));
            displayTime();
            onClickStart(v);
        }
    };

    public void onClickButton2(View v) {
        if (recentTime[1] == null) {
            Context context = getApplicationContext();
            CharSequence text = "No recent timer set";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        } else {
            seconds = recentTime[1];
            TextView b2 = (TextView) findViewById(R.id.recent_2);
            int m = seconds / 60;
            int s = seconds % 60;
            b2.setText(String.format("%d:%02d", m, s));
            displayTime();
            onClickStart(v);
        }
    };

    public void onClickButton3(View v) {
        if (recentTime[2] == null) {
            Context context = getApplicationContext();
            CharSequence text = "No recent timer set";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        } else {
            seconds = recentTime[2];
            TextView b3 = (TextView) findViewById(R.id.recent_3);
            int m = seconds / 60;
            int s = seconds % 60;
            b3.setText(String.format("%d:%02d", m, s));
            displayTime();
            onClickStart(v);
        }
    };

    public void onReset(View v) {
        seconds = 0;
        cancelTimer();
        displayTime();
    }

    public void onClickStart(View v) {
        if (seconds == 0) {
            cancelTimer();
        }
        if (timer == null) {
            // We create a new timer.
            timer = new CountDownTimer(seconds * ONE_SECOND_IN_MILLIS, ONE_SECOND_IN_MILLIS) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d(LOG_TAG, "Tick at " + millisUntilFinished);
                    seconds = Math.max(0, seconds - 1);
                    displayTime();
                }

                @Override
                public void onFinish() {
                    seconds = 0;
                    timer = null;
                    displayTime();
                }
            };
            timer.start();
            if(clicked) {
                if(recent_count == 3) recent_count = 0;
                clicked = false;
                if (!isIdent(seconds)) {
                recentTime[recent_count] = seconds;
                    if (recent_count == 0) {
                        updated = true;
                        onClickButton1(v);
                    }
                    if (recent_count == 1) {
                        updated = true;
                        onClickButton2(v);
                    }
                    if (recent_count == 2) {
                        updated = true;
                        onClickButton3(v);
                    }
                }
                    if (updated){
                    recent_count++;
                    updated = false;
                }
            }
        }
    }

    public void onClickStop(View v) {
        cancelTimer();
        displayTime();
    }

    private void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public boolean isIdent(int sec){
        boolean ident = false;
        for(int i = 0; i < recentTime.length; i++){
        if (recentTime[i] != null && recentTime[i]== sec){
            ident = true;
            return ident;
        }
            else {ident = false;}
        }
        return ident;
    }

    // Updates the time display.
    private void displayTime() {
        Log.d(LOG_TAG, "Displaying time " + seconds);
        TextView v = (TextView) findViewById(R.id.display);
        int m = seconds / 60;
        int s = seconds % 60;
        v.setText(String.format("%d:%02d", m, s));
        // Manages the buttons.
        Button stopButton = (Button) findViewById(R.id.button_stop);
        Button startButton = (Button) findViewById(R.id.button_start);
        startButton.setEnabled(timer == null && seconds > 0);
        stopButton.setEnabled(timer != null && seconds > 0);
    }


}
