package org.bearluxury.UI;

import org.bearluxury.UI.shopUI.ProductTableGUI;
import org.bearluxury.account.Account;
import org.bearluxury.account.ClerkAccountDAO;
import org.bearluxury.account.Guest;
import org.bearluxury.account.GuestAccountJDBCDAO;
import org.bearluxury.controllers.ClerkAccountController;
import org.bearluxury.controllers.GuestAccountController;
import org.bearluxury.controllers.ProductController;
import org.bearluxury.controllers.ReservationController;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductJDBCDAO;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.reservation.ReservationJDBCDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class ClerkHomePage extends HotelHomePage{


    public ClerkHomePage() {


        reservePanel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton reserveButton = new JButton("Get A Room");
        reserveButton.setFont(font);
        reserveButton.setForeground(Color.BLACK);
        reserveButton.addActionListener(new openHotelManagmentPane());
        reservePanel.add(reserveButton);

        JButton infoButton = new JButton("Modify Personal Information");
        infoButton.setFont(font);
        infoButton.setForeground(Color.BLACK);
        infoButton.addActionListener(new OpenModifyPersonalInfoPane());
        reservePanel.add(infoButton);



        JButton seeReservations = new JButton("See All Reservations");
        seeReservations.setFont(font);
        seeReservations.setForeground(Color.BLACK);
        seeReservations.addActionListener(new openViewReservationPane());

        JButton viewAccountsButton = new JButton("View Accounts");
        viewAccountsButton.setFont(font);
        viewAccountsButton.setForeground(Color.BLACK);
        viewAccountsButton.addActionListener(new openViewAccountsPane());
        reservePanel.add(viewAccountsButton);
        reservePanel.add(seeReservations);

        JButton addRoom = new JButton("Add Room");

        seeReservations.setFont(font);
        seeReservations.setForeground(Color.BLACK);
        addRoom.addActionListener(new openAddRoomPane());
        addRoom.setFont(font);
        addRoom.setForeground(Color.BLACK);
        reservePanel.add(addRoom);

        JButton viewStore = new JButton("View Store");
        viewStore.setFont(font);
        viewStore.setForeground(Color.BLACK);
        viewStore.addActionListener(new openViewStoreProductsPage());
        viewStore.setFont(font);
        viewStore.setForeground(Color.BLACK);
        reservePanel.add(viewStore);





    }
    private class openHotelManagmentPane implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openHotelManagmentSystem();
        }
    }
    private class openViewStoreProductsPage implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Close the current window
            dispose();

            // Create a ProductController instance
            ProductController productController;
            try {
                productController = new ProductController(new ProductJDBCDAO());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            // Retrieve products from the database
            Set<Product> products = productController.listProducts();

            // If the product list is empty, load products from CSV
            if (products.isEmpty()) {
                // Load products from CSV
                try {
                    productController.preFillProducts();
                    products = productController.listProducts(); // Refresh the product list
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

            // Create and display the Store Products GUI with the retrieved products
            ProductTableGUI storeProductsGUI = new ProductTableGUI(products);
            storeProductsGUI.setVisible(true);
        }
    }


    private class openViewReservationPane implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ReservationCatalog reservations = new ReservationCatalog();
                ReservationController controller = new ReservationController(new ReservationJDBCDAO());
                reservations.setReservations(controller.listReservations());
                ClerkBookedReservationsGUI catalogPane = new ClerkBookedReservationsGUI(reservations);
                catalogPane.setVisible(true);
            }
        }

    private class OpenModifyPersonalInfoPane implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Retrieve the logged-in account information using the email address
            String loggedInEmail = JOptionPane.showInputDialog(null, "Please enter your email address:");
            if (loggedInEmail != null && !loggedInEmail.isEmpty()) {
                ClerkAccountController clerkAccountController = new ClerkAccountController(new ClerkAccountDAO());
                Optional<Account> loggedInAccountOptional = clerkAccountController.getAccount(loggedInEmail);

                if (loggedInAccountOptional.isPresent()) {
                    dispose();
                    HotelManagementSystem.openClerkRegisterPane(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Account not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private class openViewAccountsPane implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GuestAccountController guestAccountController = new GuestAccountController(new GuestAccountJDBCDAO());
                GuestAccountGUI guestAccountGUI = new GuestAccountGUI(guestAccountController.listAccounts());
                guestAccountGUI.setVisible(true);
            }
        }

        private class openAddRoomPane implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddRoomPane.openAddRoomPane();
            }
        }
    }

