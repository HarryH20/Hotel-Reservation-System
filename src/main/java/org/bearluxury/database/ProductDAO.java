package org.bearluxury.database;

import java.util.Optional;

/**
 * This interface defines methods for accessing and manipulating product-related data in the database.
 * It extends the DAO interface for generic data access operations.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 * @param <T> the type of objects managed by this DAO
 */
public interface ProductDAO<T> extends DAO<T> {

    /**
     * Retrieves a product object from the database based on the specified search value.
     *
     * @param searchValue the value used to search for the product (e.g., product ID)
     * @return an Optional containing the retrieved product object, or empty if not found
     */
    Optional<T> get(int searchValue);

    /**
     * Updates an existing product in the database.
     *
     * @param t the updated product object
     * @param searchValue the value used to identify the product to be updated
     */
    void update(T t, int searchValue);

    /**
     * Deletes a product from the database based on the specified search value.
     *
     * @param searchValue the value used to identify the product to be deleted
     * @return true if the product was successfully deleted, false otherwise
     */
    boolean delete(int searchValue);
}

