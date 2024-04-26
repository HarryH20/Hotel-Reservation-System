package org.bearluxury.UI;

import org.bearluxury.account.ClerkAccountDAO;
import org.bearluxury.controllers.ClerkAccountController;

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
        JButton viewAccountsButton = new JButton("View Accounts");
        viewAccountsButton.setFont(font);
        viewAccountsButton.setForeground(Color.BLACK);
        viewAccountsButton.addActionListener(new OpenViewAccountsPane());
        reservePanel.add(viewAccountsButton);
    }
    private class openRegistration implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ClerkRegisterPain pane = new ClerkRegisterPain();
            pane.setVisible(true);
        }
    }

    private class OpenViewAccountsPane implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            ClerkAccountController clerkAccountController = new ClerkAccountController(new ClerkAccountDAO());
            ClerkAccountGUI clerkAccountGUI = new ClerkAccountGUI(clerkAccountController.listAccounts());
            clerkAccountGUI.setVisible(true);
        }
    }
}

