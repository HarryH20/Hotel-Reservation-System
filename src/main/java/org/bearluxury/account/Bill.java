package org.bearluxury.account;

import org.bearluxury.shop.Payment;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.shop.Sale;

import java.util.List;

public class Bill {
    int accountNumber;
    private Reservation reservation;
    //FIXME: Need to make a sale class
    private List<Sale> sales;

    public Bill(Reservation reservation) {
        this.reservation =reservation;
    }
    public void addSale(Sale sale){
        sales.add(sale);
    }


//    // Need to implement cost to reservation?
//    public double calculateTotalCost() {
//        double totalCost = 0.0;
//        for (Reservation reservation : reservations) {
//            //totalCost += reservation.calculateTotalCost();
//        }
//        return totalCost;
//    }
//
//    // Who will call this method?
//    //FIXME: Only prints to console
//    public void generateInvoice() {
//        // Generate and print invoice details
//        System.out.println("Invoice:");
//        for (Reservation reservation : reservations) {
//            //System.out.println("Reservation ID: " + reservation.getReservationId());
//            System.out.println("Check-in Date: " + reservation.getStartDate());
//            System.out.println("Check-out Date: " + reservation.getEndDate());
//            //System.out.println("Total Cost: " + reservation.calculateTotalCost());
//            System.out.println("---------------------");
//        }
//        System.out.println("Total Bill: " + calculateTotalCost());
//    }

    // Method to process payments for the invoice
    public void processPayment(Payment payment) {
        System.out.println("Processing payment...");
        payment.processPayment();
    }
}
