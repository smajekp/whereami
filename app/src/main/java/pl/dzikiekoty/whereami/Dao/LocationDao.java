package pl.dzikiekoty.whereami.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import pl.dzikiekoty.whereami.Model.Location;
import pl.dzikiekoty.whereami.Tables.LocationTable;

public class LocationDao implements Dao<Location> {

    private static final String INSERT_LOCATION_TABLE =
            "INSERT INTO " + LocationTable.TABLE_NAME + "("
                    + LocationTable.LONGITUDE + ","
                    + LocationTable.LATITUDE + ")"
                    + "VALUES(?,?,?)";
    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public LocationDao(SQLiteDatabase db){
        this.db = db;
        insertStatement = db.compileStatement(LocationDao.INSERT_LOCATION_TABLE);
    }

    public int save(Location location) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, location.getLongtitude());
        insertStatement.bindString(2, location.getLatitude());
        return (int) insertStatement.executeInsert();
    }

    @Override
    public void update(Location location) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(LocationTable.LONGITUDE, location.getLongtitude());
        contentValues.put(LocationTable.LATITUDE, location.getLatitude());

        db.update(LocationTable.TABLE_NAME, contentValues,
                LocationTable.ID_LOCATION + " = ?", new String[]{
                        String.valueOf(location.getIdLocation())
                });
    }

    @Override
    public void delete(int idLocation) {

            db.delete(LocationTable.TABLE_NAME,
                    LocationTable.ID_LOCATION + " = ?", new String[]{
                            String.valueOf(idLocation)
                    });

    }

    @Override
    public Location get(int idLocation) {
        Location location = null;
        Cursor cursor =
                db.query(LocationTable.TABLE_NAME,
                        new String[]{
                                LocationTable.ID_LOCATION,
                                LocationTable.LONGITUDE,
                                LocationTable.LATITUDE
                        },LocationTable.ID_LOCATION + " = ?",
                        new String[]{
                                String.valueOf(idLocation)
                        }, null, null, null, "1"
                );
        if(cursor.moveToFirst()){

            location = this.createLocationFromCursorData(cursor);
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return location;
    }

    @Override
    public List<Location> getAll() {
        List<Location> locationList = new ArrayList<Location>();
        Cursor cursor =
                db.query(LocationTable.TABLE_NAME,
                        new String[]{
                                LocationTable.ID_LOCATION,
                                LocationTable.LONGITUDE,
                                LocationTable.LATITUDE
                        }, null, null, null, null, LocationTable.LONGITUDE, null);
        //TODO
        if(cursor.moveToFirst()){
            do{
                Location location = this.createLocationFromCursorData(cursor);
                if( location != null){
                    locationList.add(location);
                }
            }while(cursor.moveToNext());
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return locationList;
    }

    public Location find(String longitude, String latitude){
        Location location = null;
        String query =
                "SELECT " + LocationTable.ID_LOCATION + ", " + LocationTable.LONGITUDE +
                        ", " + LocationTable.LATITUDE + " FROM "
                        + LocationTable.TABLE_NAME + "WHERE UPPER(" + LocationTable.LONGITUDE
                        + ") = ?  AND UPPER(" + LocationTable.LATITUDE
                        + ") = ? limit 1";
        Cursor cursor = db.rawQuery(query, new String[]{longitude.toUpperCase(), latitude.toUpperCase()});
        if (cursor.moveToFirst()) {
            location = new Location();
            location.setIdLocation(cursor.getInt(0));
            location.setLongtitude(cursor.getString(1));
            location.setLatitude(cursor.getString(2));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return location;
    }

    private Location createLocationFromCursorData(Cursor cursor) {
        Location location = null;
        if (cursor != null) {
            location = new Location();
            location.setIdLocation(cursor.getInt(0));
            location.setLongtitude(cursor.getString(1));
            location.setLatitude(cursor.getString(2));
        }
        return location;
    }


}
