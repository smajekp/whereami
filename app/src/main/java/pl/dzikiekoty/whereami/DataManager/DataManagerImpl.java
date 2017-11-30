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
            db = SQLiteDatabase.openDatabase(Environment.getDataDirectory() + "/data/pl.dzikiekoty.whereami/databases/database.db", null, SQLiteDatabase.OPEN_READWRITE);
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
//    @Override
//    public Location getLocation(int locationID) {
//        Location location = locationDAO.get(locationID);
//        if(location != null){
//            List<Group> groupList= studentGroupDAO.getGroups(studentID);
//            if(groupList.size() > 0){
//                Set<Group> selectedGroupsSet = new HashSet<Group>(groupList);
//                student.getGroups().addAll(selectedGroupsSet);
//            }
//        }
//        return location;
//    }

    @Override
    public List<Location> getLocations() {
        return locationDAO.getAll();
    }

    @Override
    public Location findLocation(String longitude, String latitude){
        return  locationDAO.find(longitude, latitude);
    }

    //TODO
//    @Override
//    public int saveLocation(Location location) {
//        int idLocation = 0;
//        try{
//            db.beginTransaction();
//            idLocation= LocationDao.save(location);
//            if(location.getGroups() != null){
//                for(Group group : student.getGroups()){
//                    int idGroup;
//                    Group dbGroup = groupDAO.find(group.getGroupName());
//                    if(dbGroup == null){
//                        idGroup = groupDAO.save(group);
//                    }else{
//                        idGroup = dbGroup.getIdGroup();
//                    }
//                    StudentGroup studentGroup = new StudentGroup(idStudent, idGroup);
//                    if(!studentGroupDAO.exists(studentGroup)){
//                        studentGroupDAO.save(studentGroup);
//                    }
//                }
//            }
//            db.setTransactionSuccessful();
//        }catch(SQLException e){
//            System.out.println("Bład" + e.getMessage());
//            idLocation = 0;
//        } finally {
//            db.endTransaction();
//        }
//        return idLocation;
//    }

    //TODO
//    @Override
//    public boolean deleteLocation(int locationID) {
//        boolean result = false;
//        try {
//            db.beginTransaction();
//
//            Location location = getLocation(locationID);
//
//            if (location != null) {
//                if( student.getGroups().size() > 0){
//                    List<Group> groupList = new ArrayList<>( student.getGroups());
//                    for (int i = 0; i < groupList.size(); i++) {
//                        studentGroupDAO.delete(new StudentGroup(student.getIdStudent(), groupList.get(i).getIdGroup()));
//                    }
//                }
//                studentDAO.delete(student);
//            }
//            db.setTransactionSuccessful();
//            result = true;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            db.endTransaction();
//        }
//        return result;
//    }

}
