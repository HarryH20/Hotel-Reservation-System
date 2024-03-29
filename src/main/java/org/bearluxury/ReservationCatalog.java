package org.bearluxury;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class ReservationCatalog {
    private List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Reservation> sortByRoomNumber(){
        List<Reservation> reservationsByNumber = reservations.stream().sorted
                (Comparator.comparing(Reservation::getRoomNumber)).collect(Collectors.toList());
        return reservationsByNumber;
    }



}
