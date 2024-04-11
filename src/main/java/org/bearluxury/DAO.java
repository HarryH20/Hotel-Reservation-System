package org.bearluxury;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DAO<T>{
    Set<T> list();

    void insert(T t) throws SQLException;

    Optional<T> get(int roomNumber);

    void update(T t, int roomNumber);

    boolean delete(int roomNumber);

    void close();

    void clear();



}