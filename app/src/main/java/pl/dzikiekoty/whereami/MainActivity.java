package pl.dzikiekoty.whereami;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToMapActivity(View v) {
        Intent intentMain = new Intent(MainActivity.this, MapActivity.class);
        MainActivity.this.startActivity(intentMain);
    }

    public void goToListActivity(View v) {
        Intent intentMain = new Intent(MainActivity.this, ListActivity.class);
        MainActivity.this.startActivity(intentMain);
    }
}
