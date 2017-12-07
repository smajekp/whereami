package pl.dzikiekoty.whereami;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.dzikiekoty.whereami.DataManager.DataManager;
import pl.dzikiekoty.whereami.DataManager.DataManagerImpl;
import pl.dzikiekoty.whereami.DataManager.OpenHelper;
import pl.dzikiekoty.whereami.Model.Location;

public class ListFragment extends Fragment
{
    ListView lv;
    Button add;
    LocationManager locationManager;
    double longitudeGPS, latitudeGPS;

    private Location loc;
    private int idLatitude;

    public DataManager dataManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        dataManager = new DataManagerImpl(getActivity());
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        add = view.findViewById(R.id.addBtn);
        idLatitude = getActivity().getIntent().getIntExtra("UniqueKeyV2",0);
        loc = new Location( 0, "", "");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                }
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
            }
        });

        ArrayList<Location> list = new ArrayList<>();
        ListAdapter adapter = new ListAdapter(getActivity(), list);
        lv = view.findViewById(R.id.list);
        Location newloc = new Location(1, "test", "test");
        adapter.add(newloc);
        //dataManager.saveLocation(newloc);
        Location newloc1 = new Location(2, "test2", "test2");
        adapter.add(newloc1);
        if(lv!=null)
            lv.setAdapter(adapter);
        return view;
    }
    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(android.location.Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();
            loc.setLongitude(String.valueOf(longitudeGPS));
            loc.setLatitude(String.valueOf(latitudeGPS));
            dataManager.saveLocation(loc);

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
}
