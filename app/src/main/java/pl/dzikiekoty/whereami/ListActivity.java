package pl.dzikiekoty.whereami;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

    public void goToMainActivityFromListActivity(View v) {
        Intent intentMain = new Intent(ListActivity.this, MainActivity.class);
        ListActivity.this.startActivity(intentMain);
    }
}
