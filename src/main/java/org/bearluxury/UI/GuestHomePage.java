package org.bearluxury.UI;

import org.bearluxury.account.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//FIXME: NEED TO MAKE HOTELHOMEPAGE GENERIC
public class GuestHomePage extends HotelHomePage {

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
                //HotelManagementSystem.openBillingPage();
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

        reservePanel.add(reserveButton);
        reservePanel.add(shopButton);
        reservePanel.add(seeReservations);
    }
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
    private class openHotelManagmentPane implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openHotelManagmentSystem();
        }
    }

}
