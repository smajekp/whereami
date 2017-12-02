package pl.dzikiekoty.whereami.DataManager;

import java.util.List;

import pl.dzikiekoty.whereami.Model.Location;

/**
 * Created by Piotr Smajek on 30.11.2017.
 */

public interface DataManager {
    Location getLocation(int locationID);
    List<Location> getLocations();
    Location findLocation(String longitude, String latitude);
    int saveLocation(Location location);
    void deleteLocation(int locationID);
}
