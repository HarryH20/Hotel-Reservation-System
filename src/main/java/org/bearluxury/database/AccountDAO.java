package org.bearluxury.database;

import java.util.Optional;

/**
 * This interface defines methods for accessing and manipulating account-related data in the database.
 * It extends the DAO interface for generic data access operations.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 * @param <T> the type of objects managed by this DAO
 */
public interface AccountDAO<T> extends DAO<T> {

    /**
     * Retrieves an account object from the database based on the specified search value.
     *
     * @param searchValue the value used to search for the account (e.g., account ID or username)
     * @return an Optional containing the retrieved account object, or empty if not found
     */
    Optional<T> get(String searchValue);

    /**
     * Updates an existing account in the database.
     *
     * @param t the updated account object
     * @param searchValue the value used to identify the account to be updated
     */
    void update(T t, String searchValue);

    /**
     * Deletes an account from the database based on the specified search value.
     *
     * @param searchValue the value used to identify the account to be deleted
     * @return true if the account was successfully deleted, false otherwise
     */
    boolean delete(String searchValue);
}
