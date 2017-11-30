package pl.dzikiekoty.whereami.Tables;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Piotr Smajek on 30.11.2017.
 */

public class LocationTable {

    public static final String TABLE_NAME = "locationsTable";
    public static final String ID_LOCATION = "id_group";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";

    public static void onCreate(SQLiteDatabase db){
        String CREATE_GROUP_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_LOCATION + " INTEGER PRIMARY KEY,"
                + LONGITUDE + " TEXT"
                + LATITUDE + " TEXT"
                + ")";
        db.execSQL(CREATE_GROUP_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        LocationTable.onCreate(db);
    }

}
