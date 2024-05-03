package org.bearluxury.controllers;

import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservationControllerTest {
    @Mock
    private ReservationJDBCDAO mockDAO;
    private ReservationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new ReservationController(mockDAO);
        controller.insertReservation(new Reservation(1234,"Alan", "Villagrand", "AV@gmail.com",3,new Date(), new Date(), true));

    }

    @Test
    void insertReservation() {
        Reservation reservation = new Reservation(4321,"Derrik", "Martinez", "DM@gmail.com",2,new Date(), new Date(), false);
        controller.insertReservation(reservation);
        verify(mockDAO, times(1)).insert(reservation);
    }


    @Test
    void listReservations() {
        Set<Reservation> reservations = new HashSet<>();
        reservations.add(new Reservation(4321,"Derrik", "Martinez", "DM@gmail.com",2,new Date(), new Date(), false));
        reservations.add(new Reservation(1032,"Wil", "Clor", "WC@gmail.com",1,new Date(), new Date(), true));
        when(mockDAO.list()).thenReturn(reservations);
        Set<Reservation> retrievedReservations = controller.listReservations();
        assertEquals(reservations, retrievedReservations);
    }

    @Test
    void updateReservations() {
        Reservation reservation = new Reservation(4321,"Derrik", "Martinez", "DM@gmail.com",2,new Date(), new Date(), false);
        controller.updateReservation(reservation,reservation.getReservationID());
        verify(mockDAO, times(1)).update(reservation, reservation.getReservationID());
    }

    @Test
    void deleteReservations() {
        Reservation deleteReservation = new Reservation(1003,"Harison", "Hassler", "DM@gmail.com",2,new Date(), new Date(), false);
        int id = deleteReservation.getReservationID();
        when(mockDAO.delete(id)).thenReturn(true);
        assertTrue(controller.deleteReservation(id));
        verify(mockDAO, times(1)).delete(id);
    }

    @Test
    void clearReservations() {
        controller.clearReservations();
        verify(mockDAO, times(1)).clear();
    }

    @Test
    void closeConnection() {
        controller.closeConnection();
        verify(mockDAO, times(1)).close();
    }
}
