package org.bearluxury.controllers;

import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.room.Room;

import java.util.Optional;
import java.util.Set;

public class ReservationController {
    private ReservationJDBCDAO reservationDAO;

    public ReservationController(ReservationJDBCDAO reservationDAO){
        this.reservationDAO = reservationDAO;
    }

    // Insert a reservation
    public void insertReservation(Reservation reservation) {
        reservationDAO.insert(reservation);
    }

    // Get a reservation by ID
    public Optional<Reservation> getReservation(int id) {
        return reservationDAO.get(id);
    }

    // Get all reservations
    public Set<Reservation> listReservations() {
        return reservationDAO.list();
    }

    public Set<Reservation> listReservations(int acctId) {
        return reservationDAO.list(acctId);
    }

    // Update a reservation by ID
    public void updateReservation(Reservation reservation, int id) {
        reservationDAO.update(reservation, id);
    }

    // Delete a reservation by ID
    public boolean deleteReservation(int id) {
        return reservationDAO.delete(id);
    }

    // Clear all reservations
    public void clearReservations() {
        reservationDAO.clear();
    }

    // Close connection
    public void closeConnection() {
        reservationDAO.close();
    }

    // Get reservations by account ID
    public Set<Reservation> listReservationsByAccountId(int accountId) {
        return reservationDAO.list(accountId);
    }

    // Get a reservation by reservation ID
    public Optional<Reservation> getReservationByReservationId(int reservationId) {
        return reservationDAO.getByReservationId(reservationId);
    }

    // Delete a reservation by reservation ID
    public boolean deleteReservationByReservationId(int reservationId) {
        return reservationDAO.deleteByReservationId(reservationId);
    }

    // Update a reservation by reservation ID
    public void updateReservationByReservationId(Reservation reservation, int reservationId) {
        reservationDAO.updateByReservationId(reservation, reservationId);
    }
}
