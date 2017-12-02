package pl.dzikiekoty.whereami;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pl.dzikiekoty.whereami.DataManager.DataManager;
import pl.dzikiekoty.whereami.DataManager.DataManagerImpl;
import pl.dzikiekoty.whereami.DataManager.OpenHelper;
import pl.dzikiekoty.whereami.Model.Location;

public class ListFragment extends Fragment
{
    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ArrayList<Location> list = new ArrayList<>();
        ListAdapter adapter = new ListAdapter(getActivity(), list);
        lv = (ListView) view.findViewById(R.id.list);
        Location newloc = new Location(1, "test", "test");
        adapter.add(newloc);
        Location newloc1 = new Location(2, "test2", "test2");
        adapter.add(newloc1);
        if(lv!=null)
            lv.setAdapter(adapter);
        return view;
    }
}
