package org.bearluxury;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

/*
 * This is the class for the reservation pane is not completed.
 * Attempting to make it work with a calendar need new maven dependacies.
 * It is in its own seperate class and called by openReservationPane().
 */
public class ReservationPane extends JFrame implements ActionListener {
    private JTextField roomId;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JComboBox<String> roomType;
    private JSpinner guestNumber;
    private JButton submitButton;
    private DatePicker checkInDatePicker;
    private DatePicker checkOutDatePicker;

    public ReservationPane(int id, LocalDate checkIn, LocalDate checkOut) {
        setTitle("Reservation Form");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(9, 2));

        JLabel roomIdLabel = new JLabel("Room ID:");
        add(roomIdLabel);
        roomId = new JTextField();
        roomId.setText(String.valueOf(id));
        roomId.setEditable(false);
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

        //DatePickerSettings checkInSettings = new DatePickerSettings();
        checkInDatePicker = new DatePicker();
        //checkInSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(1));
        checkInDatePicker.setDate(checkIn);
        checkInDatePicker.setPreferredSize(new Dimension(200, 30));
        JLabel checkInLbl = new JLabel("Check-In:");
        add(checkInLbl);
        add(checkInDatePicker);

        //DatePickerSettings checkOutSettings = new DatePickerSettings();
        checkOutDatePicker = new DatePicker();
        //checkOutSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(1));
        checkOutDatePicker.setDate(checkOut);
        checkOutDatePicker.setPreferredSize(new Dimension(200, 30));
        JLabel checkOutLbl = new JLabel("Check-Out:");
        add(checkOutLbl);
        add(checkOutDatePicker);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        add(submitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            saveToCSV();
            //JOptionPane.showMessageDialog(this, "Reservation saved successfully.");
            //dispose();
        }
    }

    public void saveToCSV() {
        String csvFileName = "ReservationList.csv";

        // Extracting the reservation data from the form
        int roomNumber = Integer.parseInt(roomId.getText());
        String guestFirstName = firstName.getText();
        String guestLastName = lastName.getText();
        String guestEmail = email.getText();
        int numberOfGuests = (int) guestNumber.getValue();
        Date startDate = java.sql.Date.valueOf(checkInDatePicker.getDate());
        Date endDate = java.sql.Date.valueOf(checkOutDatePicker.getDate());

        try {
            ReservationBuilder reservationBuilder = new ReservationBuilder(csvFileName);
            reservationBuilder.addReservation(roomNumber, guestFirstName, guestLastName, guestEmail, numberOfGuests, startDate, endDate);

            reservationBuilder.writeReservation(csvFileName);

            JOptionPane.showMessageDialog(this, "Reservation saved successfully.");
            dispose();
        } catch (RuntimeException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        ///

//        String csvReservationList = "ReservationList.csv";
//
//        try(FileWriter writer = new FileWriter(csvReservationList, true)) {
//            writer.append(roomId.getText()).append(",")
//                    .append(firstName.getText()).append(",")
//                    .append(lastName.getText()).append(",")
//                    .append(email.getText()).append(",")
//                    .append(String.valueOf(guestNumber.getValue())).append("\n");
//            JOptionPane.showMessageDialog(this,"Reservation saved successfully.");
//            dispose();
//        } catch (IOException e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(this, "Error: Could not save reservation!");
//        }
    }
    private String formatDate(java.util.Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}

