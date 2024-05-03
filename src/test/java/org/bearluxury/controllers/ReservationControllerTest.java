//package org.bearluxury.controllers;
//
//import org.bearluxury.reservation.Reservation;
//import org.bearluxury.reservation.ReservationJDBCDAO;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.sql.Date;
//import java.sql.SQLException;
//
//import static org.mockito.Mockito.*;
//
//public class ReservationControllerTest {
//    @Mock
//    private ReservationJDBCDAO mockDAO;
//    private ReservationController controller;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        controller = new ReservationController(mockDAO);
//    }
//
//    @Test
//    void insertReservation() {
//        Date startDate = new Date();
//        Date endDate = new Date();
//        Reservation reservation = new Reservation(3456, "John", "Doe", "JohnDoe@gmail.com",
//                4, startDate, endDate, false);
//        controller.insertReservation(reservation);
//        verify(mockDAO, times(1)).insert(reservation);
//    }
//
//    @Test
//    void getReservation
//}
