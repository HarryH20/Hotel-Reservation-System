package org.bearluxury.UI;

import org.bearluxury.reservation.ReservationBuilder;
import org.bearluxury.reservation.ReservationCatalog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClerkHomePage extends HotelHomePage{

    public ClerkHomePage() {
        super();

        JButton reserveButton = new JButton("Get A Room");
        reserveButton.setFont(font);
        reserveButton.setForeground(Color.BLACK);
        reserveButton.addActionListener(new ClerkHomePage.openHotelManagmentPane());
        reservePanel.add(reserveButton);

        JButton seeReservations = new JButton("See All Reservations");
        seeReservations.setFont(font);
        seeReservations.setForeground(Color.BLACK);
        seeReservations.addActionListener(new ClerkHomePage.openViewReservationPane());
        reservePanel.add(seeReservations);

        JButton addUser = new JButton("Register");
        JButton addRoom = new JButton("Add Room");

        seeReservations.setFont(font);
        seeReservations.setForeground(Color.BLACK);
        addUser.setFont(font);
        addRoom.addActionListener(new ClerkHomePage.openAddRoomPane());
        addUser.addActionListener(new ClerkHomePage.openRegistration());
        addUser.setForeground(Color.BLACK);
        addRoom.setFont(font);
        addRoom.setForeground(Color.BLACK);

        reservePanel.add(addUser);
        reservePanel.add(addRoom);
    }
    private class openHotelManagmentPane implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openHotelManagmentSystem();
        }
    }

    private class openViewReservationPane implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            ReservationCatalog reservations = new ReservationCatalog();
            reservations.setReservations(new ReservationBuilder("ReservationList.csv").getReservationList());
            BookedReservationsGUI catalogPane = new BookedReservationsGUI(reservations);
            catalogPane.setVisible(true);
        }
    }
    private class openAddRoomPane implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            AddRoomPane.openAddRoomPane();
        }
    }
    private class openRegistration implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterPane pane = new RegisterPane();
            pane.setVisible(true);
        }
    }

}
