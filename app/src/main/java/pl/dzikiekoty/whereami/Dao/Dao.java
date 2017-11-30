package pl.dzikiekoty.whereami.Dao;

import java.util.List;

/**
 * Created by Piotr Smajek on 30.11.2017.
 */

public interface Dao<T> {
    int save(T type);
    void update(T type);
    void delete(T type);
    T get(int id);
    List<T> getAll();
}

