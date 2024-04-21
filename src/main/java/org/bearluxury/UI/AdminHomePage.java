package org.bearluxury.UI;

import org.bearluxury.account.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminHomePage extends HotelHomePage{
    AdminHomePage() {
        JButton addUser = new JButton("Register New Clerk");
        addUser.setFont(font);
        addUser.addActionListener(new openRegistration());
        addUser.setForeground(Color.BLACK);
        reservePanel.add(addUser);
    }
    private class openRegistration implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterPane pane = new RegisterPane();
            pane.setVisible(true);
        }
    }
}

