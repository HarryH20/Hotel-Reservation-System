package org.bearluxury.database;

import java.util.Optional;

public interface AccountDAO<T> extends DAO<T> {
    Optional<T> get(String searchValue);

    void update(T t, String searchValue);

    boolean delete(String searchValue);
}
