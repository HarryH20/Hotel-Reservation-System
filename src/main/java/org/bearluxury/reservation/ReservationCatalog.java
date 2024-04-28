package org.bearluxury.reservation;

import org.bearluxury.room.Room;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class ReservationCatalog {
    private Set<Reservation> reservations = new TreeSet<>(Comparator.comparing(Reservation::getRoomNumber));

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }
    public boolean isAvailableDate(Room room, LocalDate startDate, LocalDate endDate){
        boolean isAvailable = true;

        Date startDateAsDate = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateAsDate = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Set<Reservation> listOfAllDatesAtRoom = reservations.stream().filter(reservation
                -> reservation.getRoomNumber() ==
                room.getRoomNumber()).collect(Collectors.toSet());

        for(Reservation r : listOfAllDatesAtRoom){


            // NO COMMENT LOL
            if(!(endDateAsDate.before(r.getStartDate()) || startDateAsDate.after(r.getEndDate()))){
               isAvailable = false;
                System.out.println("THE ROOM IS NOT AVAILIBE");
            }
        }
        return isAvailable;
    }

}
