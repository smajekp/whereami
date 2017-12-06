package pl.dzikiekoty.whereami;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class AddLocationService extends Service {
    private Toast toast;
    private Timer timer;
    private TimerTask timerTask;

    //timer for first service
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            showToast("Your service is still working");
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    // start service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        clearTimerSchedule();
        initTask();
        timer.scheduleAtFixedRate(timerTask, 8 * 1000, 8 * 1000);
        showToast("Your service has been started");
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
    private void showToast(String text) {
        toast.setText(text);
        toast.show();
    }
}
