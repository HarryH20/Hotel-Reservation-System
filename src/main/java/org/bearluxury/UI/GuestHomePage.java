package org.bearluxury.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//FIXME: NEED TO MAKE HOTELHOMEPAGE GENERIC
public class GuestHomePage extends HotelHomePage {
    public GuestHomePage() {
        super();

        JButton reserveButton = new JButton("Get A Room");
        reserveButton.setFont(font);
        reserveButton.setForeground(Color.BLACK);
        reserveButton.addActionListener(new GuestHomePage.openHotelManagmentPane());
        reservePanel.add(reserveButton);
    }
    private class openHotelManagmentPane implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openHotelManagmentSystem();
        }
    }

}
