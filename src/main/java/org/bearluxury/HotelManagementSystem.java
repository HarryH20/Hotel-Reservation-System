package org.bearluxury;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class HotelManagementSystem extends JFrame {

    ImageIcon icon;
    Color backgroundColor = new Color(232,223,185,255);
    Color blackColor = new Color(1,12,15,255);
    Color goldColor = new Color(170,141,71,255);

    Font f1 = new Font(Font.SANS_SERIF, Font.BOLD, 10);

    public HotelManagementSystem() {
        setTitle("Bear Luxury");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(backgroundColor);

        icon = new ImageIcon("logo.png");
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Current working directory: " + currentDirectory);

        setIconImage(icon.getImage());

        JPanel northPanel = new JPanel();
        northPanel.setBackground(backgroundColor);
        northPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel checkInLabel = new JLabel("Check in", SwingConstants.CENTER);
        checkInLabel.setForeground(blackColor);
        JLabel checkOutLabel = new JLabel("Check out", SwingConstants.CENTER);
        checkOutLabel.setForeground(blackColor);

        northPanel.add(checkInLabel);
        northPanel.add(checkOutLabel);

        JFormattedTextField checkInField = new JFormattedTextField(new java.text.SimpleDateFormat("E, MMM dd yyyy"));
        checkInField.setValue(new java.util.Date());
        JFormattedTextField checkOutField = new JFormattedTextField(new java.text.SimpleDateFormat("E, MMM dd yyyy"));
        checkOutField.setValue(new java.util.Date());

        northPanel.add(checkInField);
        northPanel.add(checkOutField);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(backgroundColor);
        centerPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JLabel guestsLabel = new JLabel("Guests", SwingConstants.CENTER);
        guestsLabel.setForeground(blackColor);
        JLabel roomsLabel = new JLabel("Rooms", SwingConstants.CENTER);
        roomsLabel.setForeground(blackColor);

        centerPanel.add(guestsLabel);
        centerPanel.add(roomsLabel);

        JPanel guestsPanel = new JPanel();
        guestsPanel.setBackground(backgroundColor);
        guestsPanel.setLayout(new FlowLayout());
        JButton minusGuestButton = new JButton("-");
        JTextField guestsField = new JTextField("1", 3);
        JButton plusGuestButton = new JButton("+");
        guestsPanel.add(minusGuestButton);
        guestsPanel.add(guestsField);
        guestsPanel.add(plusGuestButton);

        JPanel roomsPanel = new JPanel();
        roomsPanel.setBackground(backgroundColor);
        roomsPanel.setLayout(new FlowLayout());
        JButton minusRoomButton = new JButton("-");
        JTextField roomsField = new JTextField("1", 3);
        JButton plusRoomButton = new JButton("+");
        roomsPanel.add(minusRoomButton);
        roomsPanel.add(roomsField);
        roomsPanel.add(plusRoomButton);

        centerPanel.add(guestsPanel);
        centerPanel.add(roomsPanel);

        JButton checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.setBackground(blackColor);
        checkAvailabilityButton.setOpaque(true);
        checkAvailabilityButton.setBorderPainted(false);

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(checkAvailabilityButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        HotelManagementSystem window = new HotelManagementSystem();
        window.setVisible(true);
    }
}
