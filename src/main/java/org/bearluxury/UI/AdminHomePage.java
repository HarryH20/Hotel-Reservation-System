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
 * The AdminHomePage class represents the user interface for the admin's home page.
 * It extends the HotelHomePage class and provides functionality to register new clerks, view clerk accounts,
 * and view guest accounts.
 *
 * It contains buttons for registering new clerks, viewing clerk accounts, and viewing guest accounts.
 *
 * This class is intended to be used within the Hotel Management System application.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 *
 * @see HotelHomePage
 * @see ClerkAccountController
 * @see ClerkAccountGUI
 * @see GuestAccountClerkGUI
 * @see HotelManagementSystem
 */

public class AdminHomePage extends HotelHomePage{
    /**
     * Constructs a new AdminHomePage.
     * Initializes buttons for registering new clerks, viewing clerk accounts, and viewing guest accounts.
     */
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

    /**
     * ActionListener implementation to handle the event of opening the registration pane for new clerks.
     * Disposes of the current window and opens the clerk registration pane.
     */
    private class openRegistration implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openClerkRegisterPane(false);
        }
    }

    /**
     * ActionListener implementation to handle the event of opening the view accounts pane for clerks.
     * Disposes of the current window and opens the clerk account GUI to view clerk accounts.
     */
    private class OpenViewAccountsPane implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            ClerkAccountController clerkAccountController = new ClerkAccountController(new ClerkAccountDAO());
            ClerkAccountGUI clerkAccountGUI = new ClerkAccountGUI(clerkAccountController.listAccounts());
            clerkAccountGUI.setVisible(true);
        }
    }

    /**
     * ActionListener implementation to handle the event of opening the view guest accounts pane.
     * Disposes of the current window and opens the GUI to view guest accounts.
     */
    private class openViewGuestAccountsPane implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            dispose();
            GuestAccountClerkGUI guestAccountClerkGUI = new GuestAccountClerkGUI();
            guestAccountClerkGUI.setVisible(true);
        }
    }
}

