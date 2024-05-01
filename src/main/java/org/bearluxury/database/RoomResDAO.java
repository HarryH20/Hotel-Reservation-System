package org.bearluxury.database;

import java.util.Optional;

/**
 * This interface defines methods for accessing and manipulating room reservation-related data in the database.
 * It extends the DAO interface for generic data access operations.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 * @param <T> the type of objects managed by this DAO
 */
public interface RoomResDAO<T> extends DAO<T> {

    /**
     * Retrieves a room reservation object from the database based on the specified search value.
     *
     * @param searchValue the value used to search for the room reservation (e.g., reservation ID)
     * @return an Optional containing the retrieved room reservation object, or empty if not found
     */
    Optional<T> get(int searchValue);

    /**
     * Updates an existing room reservation in the database.
     *
     * @param t the updated room reservation object
     * @param searchValue the value used to identify the room reservation to be updated
     */
    void update(T t, int searchValue);

    /**
     * Deletes a room reservation from the database based on the specified search value.
     *
     * @param searchValue the value used to identify the room reservation to be deleted
     * @return true if the room reservation was successfully deleted, false otherwise
     */
    boolean delete(int searchValue);
}
