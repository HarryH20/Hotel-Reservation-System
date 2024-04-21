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

    public Optional<Reservation> getReservation(int roomNumber) {
        return reservationDAO.get(roomNumber);
    }

    public Set<Reservation> listReservations() {
        return reservationDAO.list();
    }

    public void updateRoom(Reservation reservation, int roomNumber) {
        reservationDAO.update(reservation, roomNumber);
    }

    public boolean deleteReservation(int roomNumber) {
        return reservationDAO.delete(roomNumber);
    }

    public void clearReservation() {
       reservationDAO.clear();
    }
    public void closeConnection() {
        reservationDAO.close();
    }
}
