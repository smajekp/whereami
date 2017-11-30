package pl.dzikiekoty.whereami.DataManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pl.dzikiekoty.whereami.Tables.LocationTable;

/**
 * Created by Piotr Smajek on 30.11.2017.
 */

public class OpenHelper extends SQLiteOpenHelper {
    private static OpenHelper instance;
    private static final int DATABASE_VERSION = 1;
    public static synchronized OpenHelper getHelper(Context context){
        if(instance == null){
            instance = new OpenHelper(context);
        }
        return instance;
    }

    OpenHelper(Context context){
        super(context, "database.db", null,DATABASE_VERSION);
    }
    //Not necessary

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LocationTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
