package org.bearluxury.UI;

import org.bearluxury.account.Role;
import org.bearluxury.controllers.ReservationController;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Set;

/**
 * Represents the graphical user interface for the guest home page.
 * Extends the HotelHomePage class.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 *
 */
public class GuestHomePage extends HotelHomePage {

    /**
     * Constructs a GuestHomePage object.
     */
    public GuestHomePage() {
        JButton reserveButton = new JButton("Get A Room");
        reserveButton.setFont(font);
        reserveButton.setForeground(Color.BLACK);
        reserveButton.addActionListener(new openHotelManagmentPane());

        JButton billButton = new JButton("Check Bill");
        billButton.setFont(font);
        billButton.setForeground(Color.BLACK);
        billButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HotelManagementSystem.openBillingPage();
            }
        });


        JButton shopButton = new JButton("Shop");
        shopButton.setFont(font);
        shopButton.setForeground(Color.BLACK);
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check to see if the user's reservation is today
                if(isValidDateForShop()) {
                    dispose();
                    HotelManagementSystem.openShopHomePage();
                }else{
                    JOptionPane jOptionPane = new JOptionPane("Can only access shop during current reservation.");
                    JDialog jD = jOptionPane.createDialog("Error!");
                    jD.setVisible(true);
                }
            }
        });

        JButton seeReservations = new JButton("See Reservations");
        seeReservations.setFont(font);
        seeReservations.setForeground(Color.BLACK);
        seeReservations.addActionListener(new GuestHomePage.openViewReservationPane());

        reservePanel.add(billButton);
        reservePanel.add(reserveButton);
        reservePanel.add(shopButton);
        reservePanel.add(seeReservations);
    }
    /**
     * Checks if the current date is valid for accessing the shop based on the user's reservations.
     *
     * @return True if the current date is valid for accessing the shop, false otherwise.
     */
    public boolean isValidDateForShop(){
        int accountId = SessionManager.getInstance().getCurrentAccount().getId();

        ReservationController rc = new ReservationController(new ReservationJDBCDAO());
        Set<Reservation> reservationSet = rc.listReservations(accountId);
        // create a new data with today's date instance
        Date today = new Date();

        boolean isValid = false;



        // checks for all the user's reservations if any are today
        for(Reservation r : reservationSet) {
            if (!(today.before(r.getStartDate()) || today.after(r.getEndDate()))) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
    /**
     * Action listener for opening the view reservation pane.
     */
    private class openViewReservationPane implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            ReservationCatalog reservations = new ReservationCatalog();
            ReservationController controller = new ReservationController(new ReservationJDBCDAO());
            reservations.setReservations(controller.listReservations());
            GuestBookedReservationsGUI catalogPane = new GuestBookedReservationsGUI(reservations);
            catalogPane.setVisible(true);
        }
    }
    /**
     * Action listener for opening the hotel management pane.
     */
    private class openHotelManagmentPane implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openHotelManagmentSystem();
        }
    }

}
