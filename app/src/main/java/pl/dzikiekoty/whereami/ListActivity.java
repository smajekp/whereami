package pl.dzikiekoty.whereami;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListFragment lf = new ListFragment();
        fragmentTransaction.add(R.id.container, lf, null);
        fragmentTransaction.commit();
    }

    public void goToMainActivityFromListActivity(View v) {
        Intent intentMain = new Intent(ListActivity.this, MainActivity.class);
        ListActivity.this.startActivity(intentMain);
    }
}
