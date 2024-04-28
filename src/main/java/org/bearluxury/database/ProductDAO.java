package org.bearluxury.database;

import java.util.Optional;

public interface ProductDAO<T> extends DAO<T> {
    Optional<T> get(int searchValue);


    void update(T t, int searchValue);

    boolean delete(int searchValue);
}
