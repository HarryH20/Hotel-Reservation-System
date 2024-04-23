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
    public void insertReservation(Reservation reservation) {
        reservationDAO.insert(reservation);
    }

    public Optional<Reservation> getReservation(int id) {
        return reservationDAO.get(id);
    }

    public Set<Reservation> listReservations() {
        return reservationDAO.list();
    }

    public void updateRoom(Reservation reservation, int id) {
        reservationDAO.update(reservation, id);
    }

    public boolean deleteReservation(int id) {
        return reservationDAO.delete(id);
    }

    public void clearReservation() {
       reservationDAO.clear();
    }
    public void closeConnection() {
        reservationDAO.close();
    }

    public Set<Reservation> listReservations(int accountId) {
        return reservationDAO.list(accountId);
    }
}
