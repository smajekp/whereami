package pl.dzikiekoty.whereami;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class AddLocationService extends Service {
    private Toast toast;
    private Timer timer;
    private TimerTask timerTask;
    private int value;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    private Handler handler = new Handler();

    //timer for first service
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            handler.post(new Runnable() {
                public void run() {
                   // showToast();
                }
            });

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        //toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    // start service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        clearTimerSchedule();
        initTask();
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        String strValue = sharedpreferences.getString(Name, "");
        if (strValue != "") {
            value = Integer.parseInt(strValue);
            timer.scheduleAtFixedRate(timerTask, value * 1000, value * 1000);
            //Toast.makeText(getApplicationContext(), "Your service has been started", Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    // clear timer
    private void clearTimerSchedule() {
        if(timerTask != null) {
            timerTask.cancel();
            timer.purge();
        }
    }

    // init timer task
    private void initTask() {
        timerTask = new MyTimerTask();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    //show toast method
    private void showToast() {
        handler.post(new Runnable() {
            public void run() {
                sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                String strValue = sharedpreferences.getString(Name, "");
                if (strValue != "") {
                    value = Integer.parseInt(strValue);
                    //Toast.makeText(getApplicationContext(), " Wartosc " + value, Toast.LENGTH_SHORT).show();
                }
            };
        });
    }
}
