package org.bearluxury;

import org.bearluxury.reservation.Reservation;

import java.util.ArrayList;
import java.util.List;

public class Billing {
    private List<Reservation> reservations;

    public Billing() {
        this.reservations = new ArrayList<>();
    }

    // Method to add a reservation to the billing
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    // Need to implement cost to reservation?
    public double calculateTotalCost() {
        double totalCost = 0.0;
        for (Reservation reservation : reservations) {
            //totalCost += reservation.calculateTotalCost();
        }
        return totalCost;
    }

    // Who will call this method?
    public void generateInvoice() {
        // Generate and print invoice details
        System.out.println("Invoice:");
        for (Reservation reservation : reservations) {
            //System.out.println("Reservation ID: " + reservation.getReservationId());
            System.out.println("Check-in Date: " + reservation.getStartDate());
            System.out.println("Check-out Date: " + reservation.getEndDate());
            //System.out.println("Total Cost: " + reservation.calculateTotalCost());
            System.out.println("---------------------");
        }
        System.out.println("Total Bill: " + calculateTotalCost());
    }

    // Method to process payments for the invoice
    public void processPayment(Payment payment) {
        System.out.println("Processing payment...");
        payment.processPayment();
    }
}
