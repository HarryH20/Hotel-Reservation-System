package org.bearluxury;

import com.github.lgooddatepicker.components.DatePicker;
import java.util.Date;

class Reservation {
    private int roomNumber;
    private String firstName;
    private String lastName;
    private String email;
    private int numberOfGuests;
    private DatePicker startDate;
    private DatePicker endDate;


    public Reservation(){}
    public Reservation(int roomNumber, String firstName,
                       String lastName, String email,
                       int numberOfGuests, DatePicker startDate,
                       DatePicker endDate) {
        this.roomNumber = roomNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfGuests = numberOfGuests;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return roomNumber            +
                "," + firstName      +
                "," + lastName       +
                "," + email          +
                "," + numberOfGuests +
                "," + startDate      +
                "," + endDate        +
                '\n';
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }


    public DatePicker getStartDate() {
        return startDate;
    }

    public void setStartDate(DatePicker startDate) {
        this.startDate = startDate;
    }

    public DatePicker getEndDate() {
        return endDate;
    }

    public void setEndDate(DatePicker endDate) {
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
}
