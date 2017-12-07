package pl.dzikiekoty.whereami.Model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Piotr Smajek on 30.11.2017.
 */

public class Location {

    private int idLocation;
    private String longitude;
    private String latitude;

    public Location() {
    }

    public Location(int idLocation, String longtitude, String latitude) {
        this.idLocation = idLocation;
        this.longitude = longtitude;
        this.latitude = latitude;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longtitude) {
        this.longitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
