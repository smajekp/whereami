package pl.dzikiekoty.whereami;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import pl.dzikiekoty.whereami.Model.Location;

public class ListAdapter extends ArrayAdapter<Location>
{
    public ListAdapter(Context context, List<Location> loc) {
        super(context, 0, loc);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Location loc = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
        }
        TextView longtitude = convertView.findViewById(R.id.Row1);
        TextView latitude = convertView.findViewById(R.id.Row2);
        longtitude.setText(loc.getLongtitude());
        latitude.setText(loc.getLatitude());
        return convertView;
    }
}
