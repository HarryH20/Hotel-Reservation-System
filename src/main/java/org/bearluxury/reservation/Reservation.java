package org.bearluxury.reservation;


import org.bearluxury.account.Account;
import org.bearluxury.state.SessionManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class Reservation {
    private int roomNumber;
    private String firstName;
    private String lastName;
    private String email;
    private int numberOfGuests;
    private Date startDate;
    private Date endDate;

    private boolean checkedIn;
    private int id;

    private int reservationID;

    public Reservation(){}
    public Reservation(int roomNumber, String firstName,
                       String lastName, String email,
                       int numberOfGuests, Date startDate,
                       Date endDate, boolean checkedIn) {
        this.roomNumber = roomNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfGuests = numberOfGuests;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
        this.checkedIn = checkedIn;

    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = dateFormat.format(startDate);
        String endDateStr = dateFormat.format(endDate);

        return roomNumber            +
                "," + firstName      +
                "," + lastName       +
                "," + email          +
                "," + numberOfGuests +
                // Date Format: yyyy-MM-dd
                "," + startDateStr +
                "," + endDateStr +
                '\n';
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
    public void setID(int id){
        this.id = id;
    }
    public int getId(){return id;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                roomNumber == that.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, roomNumber);
    }


    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }


    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }
}
