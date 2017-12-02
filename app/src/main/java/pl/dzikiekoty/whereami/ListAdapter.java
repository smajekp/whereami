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

public class ListAdapter extends ArrayAdapter
{
    private Context context;
    private List<Location> loc;

    public ListAdapter(Context context, int textViewResourceId, List objects) {
        super(context,textViewResourceId, objects);

        this.context = context;
        loc = objects;

    }

    private class ViewHolder
    {
        TextView longtitude;
        TextView latitude;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.row, null);

            holder = new ViewHolder();
            holder.longtitude = (TextView) convertView.findViewById(R.id.Row1);
            holder.latitude = (TextView) convertView.findViewById(R.id.Row2);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Location lc = loc.get(position);
        holder.longtitude.setText("Car Place: " +  lc.getLongtitude() + "");
        holder.latitude.setText("Car Name: "+ lc.getLatitude()+"");
        return convertView;


    }
}
