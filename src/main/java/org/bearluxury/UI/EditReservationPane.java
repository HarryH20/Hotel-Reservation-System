package org.bearluxury.UI;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import org.bearluxury.controllers.ReservationController;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationJDBCDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class EditReservationPane extends JFrame{

    Reservation toChange;

    DefaultTableModel model;

    JTable table;

    LocalDate startDate;

    LocalDate endDate;

    public EditReservationPane(Reservation toChange, DefaultTableModel model, JTable table){
        this.table = table;
        this.model = model;
        this.toChange = toChange;
        setTitle("Edit Reservation");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(9, 2));

        JLabel firstNameLabel = new JLabel("First Name");
        JTextField firstNameField = new JTextField();

        JLabel lastNameLabel = new JLabel("Last Name");
        JTextField lastNameField = new JTextField();

        JLabel emailLabel = new JLabel("Email");
        JTextField emailField = new JTextField();

        JLabel guestLabel = new JLabel("Number of Guests");
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 8, 1);
        JSpinner guestNumber = new JSpinner(spinnerModel);

        JLabel startLabel = new JLabel("Start Date");
        DatePickerSettings checkInDatePickerSettings = new DatePickerSettings();

        DatePicker checkInDatePicker = new DatePicker(checkInDatePickerSettings);
        checkInDatePickerSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(1));
        startDate = Instant.ofEpochMilli(toChange.getStartDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        checkInDatePicker.setDate(startDate);
        checkInDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent event) {
                startDate = checkInDatePicker.getDate();
            }
        });

        JLabel endLabel = new JLabel("End Date");
        DatePickerSettings checkOutDatePickerSettings = new DatePickerSettings();

        DatePicker checkOutDatePicker = new DatePicker(checkOutDatePickerSettings);
        checkOutDatePickerSettings.setDateRangeLimits(LocalDate.now(), LocalDate.now().plusYears(1));
        endDate = Instant.ofEpochMilli(toChange.getEndDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        checkOutDatePicker.setDate(endDate);
        checkOutDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent event) {
                endDate = checkOutDatePicker.getDate();
            }
        });

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationController controller = new ReservationController(new ReservationJDBCDAO());
                SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
                Reservation res = new Reservation(toChange.getRoomNumber(),
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        Integer.parseInt(guestNumber.getValue().toString()),
                        java.sql.Date.valueOf(startDate),
                        java.sql.Date.valueOf(endDate));

                controller.updateRoom(res,toChange.getRoomNumber());

                model.removeRow(table.getSelectedRow());

                model.addRow(new Object[]{
                        res.getRoomNumber(),
                        res.getFirstName(),
                        res.getLastName(),
                        res.getEmail(),
                        res.getNumberOfGuests(),
                        formatter.format(res.getStartDate()),
                        formatter.format(res.getEndDate())
                });
            }

        });

        add(firstNameLabel);
        add(firstNameField);
        add(lastNameLabel);
        add(lastNameField);
        add(emailLabel);
        add(emailField);
        add(guestLabel);
        add(guestNumber);
        add(startLabel);
        add(checkInDatePicker);
        add(endLabel);
        add(checkOutDatePicker);
        add(submitButton);

    }



}
