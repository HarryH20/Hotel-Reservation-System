package org.bearluxury.database;
import org.bearluxury.account.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This interface defines generic data access operations for managing objects of type T in the database.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 * @param <T> the type of objects managed by this DAO
 */
public interface DAO<T> {

    /**
     * Retrieves a set of all objects of type T from the database.
     *
     * @return a set containing all objects of type T in the database
     */
    Set<T> list();

    /**
     * Inserts a new object of type T into the database.
     *
     * @param t the object to be inserted into the database
     * @throws SQLException if an SQL exception occurs while inserting the object
     */
    void insert(T t) throws SQLException;

    /**
     * Closes any resources associated with this DAO.
     */
    void close();

    /**
     * Clears all objects of type T from the database.
     */
    void clear();
}
