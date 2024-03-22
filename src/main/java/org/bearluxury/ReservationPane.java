package org.bearluxury;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
