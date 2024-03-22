package org.bearluxury;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class HotelManagementSystem extends JFrame {

    ImageIcon icon;
    Color backgroundColor = new Color(232,223,185,255);
    Color blackColor = new Color(1,12,15,255);
    //Color goldColor = new Color(170,141,71,255);

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

        /*
        * Reserve Button Addition
        */
        JButton reserveButton = new JButton("Reserve");
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openReservationPane();
            }
        });
        JPanel reservePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        reservePanel.add(reserveButton);
        add(reservePanel, BorderLayout.SOUTH);
    }


    public static void openRoomCatalogPane(){
        RoomCatalog roomCatalog = new RoomCatalog();
        roomCatalog.setRooms(new RoomBuilder("RoomList.csv").getRoomList());
        AvaliableRoomsGUI display = new AvaliableRoomsGUI(roomCatalog,1);
        display.setVisible(true);

    }

    /*
    * Added this method to open the reservation panel.
    * Can help with being able to have a reserve button on
    * multiple different panes
    */
    public static void openReservationPane() {
        ReservationPane reservationPane = new ReservationPane();
        reservationPane.setVisible(true);
    }
    public static void main(String[] args) {
        //HotelManagementSystem window = new HotelManagementSystem();
        HotelManagementSystem.openRoomCatalogPane();
        //window.setVisible(true);
    }

}

/*
* This is the class for the reservation pane is not completed.
* Attempting to make it work with a calendar need new maven dependacies
*/
class ReservationPane extends JFrame implements ActionListener {
    private JTextField roomId;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JComboBox<String> roomType;
    private JSpinner guestNumber;
//    private JDateChooser startDate;
//    private JDateChooser endDate;
    private JButton submitButton;

    public ReservationPane() {
        setTitle("Reservation Form");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(9, 2));

        JLabel roomIdLabel = new JLabel("Room ID:");
        add(roomIdLabel);
        roomId = new JTextField();
        add(roomId);

        JLabel firstNameLabel = new JLabel("First Name:");
        add(firstNameLabel);
        firstName = new JTextField();
        add(firstName);

        JLabel lastNameLabel = new JLabel("Last Name:");
        add(lastNameLabel);
        lastName = new JTextField();
        add(lastName);

        JLabel emailLabel = new JLabel("Email:");
        add(emailLabel);
        email = new JTextField();
        add(email);

        JLabel guestsNumberLabel = new JLabel("Guests Number:");
        add(guestsNumberLabel);
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 8, 1);
        guestNumber = new JSpinner(spinnerModel);
        add(guestNumber);

//        JLabel startDateLabel = new JLabel("Start Date:");
//        add(startDateLabel);
//        startDate = new JDateChooser();
//        add(startDate);
//
//        JLabel endDateLabel = new JLabel("End Date:");
//        add(endDateLabel);
//        endDate = new JDateChooser();
//        add(endDate);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        add(submitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            //saveToCSV();
            JOptionPane.showMessageDialog(this, "Reservation saved successfully.");
            dispose();
        }
    }

    private void saveToCSV() {
        //FIX
    }
    private String formatDate(java.util.Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}
