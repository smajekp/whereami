package pl.dzikiekoty.whereami.DataManager;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.dzikiekoty.whereami.Dao.LocationDao;
import pl.dzikiekoty.whereami.Model.Location;

/**
 * Created by Piotr Smajek on 30.11.2017.
 */

public class DataManagerImpl implements DataManager{

    private Context context;
    private SQLiteDatabase db;

    private LocationDao locationDAO;

    public DataManagerImpl(Context context){
        this.context = context;
        SQLiteOpenHelper openHelper = new OpenHelper(this.context);
        db = openHelper.getWritableDatabase();

        locationDAO = new LocationDao(db);
    }
    private void openDatabase() {
        if (!db.isOpen()) {
            db = SQLiteDatabase.openDatabase(Environment.getDataDirectory() + "/data/pl.dzikiekoty.whereami/databases/databaselocation.db", null, SQLiteDatabase.OPEN_READWRITE);
            locationDAO = new LocationDao(db);
        }
    }
    private void closeDatabase() {
        if (db.isOpen()) {
            db.close();
        }
    }
    private void resetDb() {
        closeDatabase();
        SystemClock.sleep(500);
        openDatabase();
    }

    //TODO
    @Override
    public Location getLocation(int locationID) {
        Location location = locationDAO.get(locationID);
            return location;
    }

    @Override
    public List<Location> getLocations() {
        return locationDAO.getAll();
    }

    @Override
    public Location findLocation(String longitude, String latitude){
        return  locationDAO.find(longitude, latitude);
    }

    //TODO
    @Override
    public int saveLocation(Location location) {
        int idLocation = 0;
        try{
            db.beginTransaction();
            idLocation= locationDAO.save(location);
            db.setTransactionSuccessful();
        }catch(SQLException e){
            System.out.println("Bład" + e.getMessage());
            idLocation = 0;
        } finally {
            db.endTransaction();
        }
        return idLocation;
    }

    //TODO
    @Override
    public void deleteLocation(int locationID) {
        locationDAO.delete(locationID);
    }

}
