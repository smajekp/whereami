package pl.dzikiekoty.whereami;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.dzikiekoty.whereami.DataManager.DataManager;
import pl.dzikiekoty.whereami.DataManager.DataManagerImpl;
import pl.dzikiekoty.whereami.Model.Location;

public class ListAdapter extends ArrayAdapter<Location>
{
    Location locc;
    ListFragment fragment;
    public ListAdapter(Context context, List<Location> loc, ListFragment frgm){
        super(context, 0, loc);
        fragment = frgm;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Location loc = getItem(position);
        locc = loc;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        TextView longitude = convertView.findViewById(R.id.Row1);
        TextView latitude = convertView.findViewById(R.id.Row2);
        Button deleteBtn = (Button)convertView.findViewById(R.id.button2);
        longitude.setText("Longitude: " + loc.getLongitude() + "");
        latitude.setText("Latitude: " + loc.getLatitude());

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fragment.deletePos(locc.getIdLocation());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
