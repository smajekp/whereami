package pl.dzikiekoty.whereami;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import pl.dzikiekoty.whereami.DataManager.DataManager;
import pl.dzikiekoty.whereami.DataManager.DataManagerImpl;

public class AddLocationService extends Service implements LocationListener {
    private Toast toast;
    private Timer timer;
    private TimerTask timerTask;
    private int value;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    private Handler handler = new Handler();
    LocationManager locationManager;
    boolean isGPSEnabled = false;
    pl.dzikiekoty.whereami.Model.Location currentLocation;

    double longitudeGPS, latitudeGPS;

    private pl.dzikiekoty.whereami.Model.Location loc;
    boolean isNetworkEnabled = false;
    public DataManager dataManager;

    boolean canGetLocation = false;
    Location location;
    double latitude;
    double longitude;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    public Location getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    //timer for first service
    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            handler.post(new Runnable() {
                public void run() {
                    showToast();
                }
            });

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        dataManager = new DataManagerImpl(getApplicationContext());
        //toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    // start service
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        clearTimerSchedule();
        initTask();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 8000, this); //  User's location is retrieve every 3 seconds

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else{
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 8000, this);
        }


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        String strValue = sharedpreferences.getString(Name, "");
        if (strValue != "") {
            value = Integer.parseInt(strValue);
            timer.scheduleAtFixedRate(timerTask, value * 1000, value * 1000);
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

//        currentLocation = new pl.dzikiekoty.whereami.Model.Location();
//        currentLocation.setLongitude(String.valueOf(location.getLongitude()));
//        currentLocation.setLatitude(String.valueOf(location.getLatitude()));
//        dataManager.saveLocation(currentLocation);
        handler.post(new Runnable() {
            public void run() {
                sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                String strValue = sharedpreferences.getString(Name, "");
                if (strValue != "0") {

                    location = getLocation();
                    loc = new pl.dzikiekoty.whereami.Model.Location( 0, "", "");
                    locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListenerGPS);
                    android.location.Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListenerGPS);
                    longitudeGPS = location.getLongitude();
                    latitudeGPS = location.getLatitude();
                    loc.setLongitude(String.valueOf(longitudeGPS));
                    loc.setLatitude(String.valueOf(latitudeGPS));
                    dataManager.saveLocation(loc);

                    value = Integer.parseInt(strValue);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.location_service_saved_toast), Toast.LENGTH_SHORT).show();
                }
            };
        });
    }

    public final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(android.location.Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}
