package pl.dzikiekoty.whereami;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.app.Fragment;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SettingsFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener {
    LocationManager locationManager;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        String strValue = sharedpreferences.getString(Name, "");
        int nInt = 0;
        if (strValue != null && strValue != "") {
            nInt = Integer.parseInt(strValue);
        }

        if (strValue != null && nInt > 0 ) {
            // initialize service
            Intent serviceIntent = new Intent(this, AddLocationService.class);
            startService(serviceIntent);
        }

        locationManager  = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        checkLocation();

        Button mClickButton1 = (Button)findViewById(R.id.homeBtn);
        mClickButton1.setOnClickListener(this);
        Button mClickButton2 = (Button)findViewById(R.id.settingsBtn);
        mClickButton2.setOnClickListener(this);
        Button mClickButton3 = (Button)findViewById(R.id.listBtn);
        mClickButton3.setOnClickListener(this);

        // set welcome screen on start
        HomeFragment fragment0 = new HomeFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.linear, fragment0);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    public void goToMapActivity(View v) {
        Intent intentMain = new Intent(MainActivity.this, MapActivity.class);
        MainActivity.this.startActivity(intentMain);
    }


    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                        System.exit(0);
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.homeBtn: {
                HomeFragment fragment0 = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.linear, fragment0);
                transaction.addToBackStack(null);

                transaction.commit();
                break;
            }
            case  R.id.settingsBtn: {
                SettingsFragment fragment1 = new SettingsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.linear, fragment1);
                transaction.addToBackStack(null);

                transaction.commit();
                break;
            }

            case R.id.listBtn: {
                ListFragment fragment3 = new ListFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.linear, fragment3);
                transaction.addToBackStack(null);

               transaction.commit();
                break;
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
