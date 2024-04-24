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

//FIXME: NEED TO MAKE HOTELHOMEPAGE GENERIC
public class GuestHomePage extends HotelHomePage {

    public GuestHomePage() {
        JButton reserveButton = new JButton("Get A Room");
        reserveButton.setFont(font);
        reserveButton.setForeground(Color.BLACK);
        reserveButton.addActionListener(new openHotelManagmentPane());
        JButton shopButton = shopButton = new JButton("Shop");
        shopButton.setFont(font);
        shopButton.setForeground(Color.BLACK);
        shopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        Date today = new Date();

        boolean isValid = false;

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
