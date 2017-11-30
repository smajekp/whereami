package pl.dzikiekoty.whereami.Model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Piotr Smajek on 30.11.2017.
 */

public class Location {

    private int idLocation;
    private String longtitude;
    private String latitude;

    public Location() {
    }

    public Location(int idLocation, String longtitude, String latitude) {
        this.idLocation = idLocation;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
