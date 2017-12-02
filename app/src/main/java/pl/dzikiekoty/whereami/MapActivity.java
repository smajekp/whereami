package pl.dzikiekoty.whereami;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pl.dzikiekoty.whereami.R;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public void goToMainActivity(View v) {
        Intent intentMain = new Intent(MapActivity.this, MainActivity.class);
        MapActivity.this.startActivity(intentMain);
    }
}
