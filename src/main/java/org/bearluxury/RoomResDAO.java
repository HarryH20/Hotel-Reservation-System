package org.bearluxury;

import java.util.Optional;

public interface RoomResDAO<T> extends DAO<T>{
    Optional<T> get(int searchValue);


    void update(T t, int searchValue);

    boolean delete(int searchValue);
}
