package org.bearluxury.UI;

import org.bearluxury.account.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//FIXME: NEED TO MAKE HOTELHOMEPAGE GENERIC
public class GuestHomePage extends HotelHomePage {

    public GuestHomePage(Role role) {
        super(role);
        JButton reserveButton = new JButton("Get A Room");
        reserveButton.setFont(font);
        reserveButton.setForeground(Color.BLACK);
        reserveButton.addActionListener(new openHotelManagmentPane(role));
        reservePanel.add(reserveButton);
    }
    private class openHotelManagmentPane implements ActionListener {
        Role role;
        public openHotelManagmentPane(Role role){
            this.role = role;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openHotelManagmentSystem(role);
        }
    }

}
