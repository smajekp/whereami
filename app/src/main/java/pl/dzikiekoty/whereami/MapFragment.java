package pl.dzikiekoty.whereami;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.dzikiekoty.whereami.Dao.LocationDao;
import pl.dzikiekoty.whereami.DataManager.DataManagerImpl;
import pl.dzikiekoty.whereami.Model.Location;
import pl.dzikiekoty.whereami.DataManager.DataManager;
import pl.dzikiekoty.whereami.DataManager.DataManagerImpl;
import pl.dzikiekoty.whereami.DataManager.OpenHelper;
import pl.dzikiekoty.whereami.Model.Location;

/**
 * Created by Santa on 07.12.2017.
 */


public class MapFragment extends Fragment {

    public static DataManager db;


    private GoogleMap googleMap;
    MapView mMapView;
    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    public double l1, l2, l3, l4;
    private Location loc, loc2;
    private List<Location> loclist;
    private LatLng pres;
    private List<LatLng> ltln = new ArrayList();
    private Marker marker1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new DataManagerImpl(getActivity());


        View view = inflater.inflate(R.layout.fragment_map, container, false);

        loc = new Location(0, "", "");
        loclist = db.getLocations();
        Collections.reverse(loclist);


        for (int i = 0; i < loclist.size(); i++) {
            pres = new LatLng(
                    Double.parseDouble(String.format("%.3f", Double.parseDouble(loclist.get(i).getLongitude()))),
                    Double.parseDouble(String.format("%.3f", Double.parseDouble(loclist.get(i).getLatitude()))));
            ltln.add(pres);
        }


        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

//                mMap.setOnMyLocationButtonClickListener(this);
//                mMap.setOnMyLocationClickListener(this);

                for (int ai = 0; ai < ltln.size(); ai++) {


                    marker1 = googleMap.addMarker(new MarkerOptions().position(ltln.get(ai))
                            .title(loc.getLongitude()));

                }


                if (ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                }
                googleMap.setMyLocationEnabled(true);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(ltln.get(0)).zoom(5).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

//    @Override
//    public void onMyLocationClick(@NonNull Location location) {
//        Toast.makeText(myContext, "Current location:\n" + location, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public boolean onMyLocationButtonClick() {
//        Toast.makeText(myContext, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
//        // Return false so that we don't consume the event and the default behavior still occurs
//        // (the camera animates to the user's current position).
//        return false;
//    }
}
