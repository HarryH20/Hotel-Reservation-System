package org.bearluxury;
import java.util.List;
import java.util.Optional;

public interface DAO<T>{
    List<T> list();

    void create(T t);

    Optional<T> get(int roomNumber);

    void update(T t, int roomNumber);

    void delete(int roomNumber);



}
