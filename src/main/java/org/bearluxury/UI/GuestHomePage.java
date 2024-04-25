package org.bearluxury.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                dispose();
                HotelManagementSystem.openShopHomePage();
            }
        });

        reservePanel.add(reserveButton);
        reservePanel.add(shopButton);
        reservePanel.add(billButton);
    }
    private class openHotelManagmentPane implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openHotelManagmentSystem();
        }
    }

}
