package org.bearluxury.controllers;

import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.room.Room;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
/**
 * The reservation controller provides methods to interact with reservation data in the database.
 * It utilizes a ReservationJDBCDAO object for database operations.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class ReservationController {
    private ReservationJDBCDAO reservationDAO;

    /**
     * Constructs a ReservationController with the specified ReservationJDBCDAO.
     *
     * @param reservationDAO the ReservationJDBCDAO instance to be used for database operations
     */
    public ReservationController(ReservationJDBCDAO reservationDAO){
        this.reservationDAO = reservationDAO;
    }

    /**
     * Inserts a new reservation into the database.
     *
     * @param reservation the reservation to be inserted
     */
    public void insertReservation(Reservation reservation) {
        reservationDAO.insert(reservation);
    }

    /**
     * Retrieves a reservation from the database based on its ID.
     *
     * @param id the ID of the reservation to retrieve
     * @return an Optional containing the retrieved reservation, or empty if not found
     */
    public Optional<Reservation> getReservation(int id) {
        return reservationDAO.get(id);
    }

    /**
     * Retrieves a set of all reservations stored in the database.
     *
     * @return a Set containing all reservations stored in the database
     */
    public Set<Reservation> listReservations() {
        return reservationDAO.list();
    }

    /**
     * Retrieves a set of reservations associated with a specific account ID.
     *
     * @param acctId the account ID to retrieve reservations for
     * @return a Set containing reservations associated with the specified account ID
     */
    public Set<Reservation> listReservations(int acctId) {
        return reservationDAO.list(acctId);
    }

    /**
     * Updates an existing reservation in the database.
     *
     * @param reservation the updated reservation
     * @param id the ID of the reservation to be updated
     */
    public void updateReservation(Reservation reservation, int id) {
        reservationDAO.update(reservation, id);
    }

    /**
     * Deletes a reservation from the database based on its ID.
     *
     * @param id the ID of the reservation to delete
     * @return true if the reservation was successfully deleted, false otherwise
     */
    public boolean deleteReservation(int id) {
        return reservationDAO.delete(id);
    }

    /**
     * Clears all reservations from the database.
     */
    public void clearReservations() {
        reservationDAO.clear();
    }

    /**
     * Closes the connection to the database.
     */
    public void closeConnection() {
        reservationDAO.close();
    }

    /**
     * Retrieves a set of reservations associated with a specific account ID.
     *
     * @param accountId the account ID to retrieve reservations for
     * @return a Set containing reservations associated with the specified account ID
     */
    public Set<Reservation> listReservationsByAccountId(int accountId) {
        return reservationDAO.list(accountId);
    }

    /**
     * Retrieves a reservation from the database based on its reservation ID.
     *
     * @param reservationId the reservation ID to retrieve
     * @return an Optional containing the retrieved reservation, or empty if not found
     */
    public Optional<Reservation> getReservationByReservationId(int reservationId) {
        return reservationDAO.getByReservationId(reservationId);
    }

    /**
     * Deletes a reservation from the database based on its reservation ID.
     *
     * @param reservationId the reservation ID to delete
     * @return true if the reservation was successfully deleted, false otherwise
     */
    public boolean deleteReservationByReservationId(int reservationId) {
        return reservationDAO.deleteByReservationId(reservationId);
    }

    /**
     * Updates an existing reservation in the database based on its reservation ID.
     *
     * @param reservation the updated reservation
     * @param reservationId the reservation ID to be updated
     */
    public void updateReservationByReservationId(Reservation reservation, int reservationId) {
        reservationDAO.updateByReservationId(reservation, reservationId);
    }

    /**
     * Checks if there are any conflicting reservations for a specific room within the specified date range.
     *
     * @param roomNumber the number of the room to check for conflicting reservations
     * @param startDate  the start date of the reservation range
     * @param endDate    the end date of the reservation range
     * @return true if there are conflicting reservations, false otherwise
     */
    public boolean hasConflictingReservations(int roomNumber, LocalDate startDate, LocalDate endDate) {
        return reservationDAO.hasConflictingReservations(roomNumber, startDate, endDate);
    }
}