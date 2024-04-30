package org.bearluxury.UI;

import org.bearluxury.account.ClerkAccountDAO;
import org.bearluxury.account.GuestAccountJDBCDAO;
import org.bearluxury.controllers.ClerkAccountController;
import org.bearluxury.controllers.GuestAccountController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class AdminHomePage extends HotelHomePage{
    AdminHomePage() {
        JButton addUser = new JButton("Register New Clerk");
        addUser.setFont(font);
        addUser.addActionListener(new openRegistration());
        addUser.setForeground(Color.BLACK);
        reservePanel.add(addUser);
        JButton viewClerkAccounts = new JButton("View Clerk Accounts");
        viewClerkAccounts.setFont(font);
        viewClerkAccounts.setForeground(Color.BLACK);
        viewClerkAccounts.addActionListener(new OpenViewAccountsPane());
        reservePanel.add(viewClerkAccounts);
        JButton viewGuestAccounts = new JButton("View Guest Accounts");
        viewGuestAccounts.setFont(font);
        viewGuestAccounts.setForeground(Color.BLACK);
        viewGuestAccounts.addActionListener(new openViewGuestAccountsPane());

        reservePanel.add(viewGuestAccounts);
    }
    private class openRegistration implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openClerkRegisterPane(false);
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

    private class openViewGuestAccountsPane implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            dispose();
            GuestAccountClerkGUI guestAccountClerkGUI = new GuestAccountClerkGUI();
            guestAccountClerkGUI.setVisible(true);
        }
    }
}

