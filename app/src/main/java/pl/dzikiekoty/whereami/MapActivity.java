package pl.dzikiekoty.whereami;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.dzikiekoty.whereami.R;

public class MapActivity extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);



        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap googleMap) {
//
//                //googleMap.addMarker(new MarkerOptions().title("Paris").position(PARIS));
//
//                //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PARIS, 15));
//                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//
//            }
//        });
    }

    public void goToMainActivity(View v) {
        Intent intentMain = new Intent(MapActivity.this, MainActivity.class);
        MapActivity.this.startActivity(intentMain);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        LatLng sydney = new LatLng(-33.852, 151.211);
        map.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}
