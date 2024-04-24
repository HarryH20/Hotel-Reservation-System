package org.bearluxury.database;
import org.bearluxury.account.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DAO<T>{
    Set<T> list();

    void insert(T t) throws SQLException;

    void close();

    void clear();



}