package org.bearluxury.UI;

import org.bearluxury.controllers.ReservationController;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Set;

public class GuestBookedReservationsGUI extends BookedReservationsGUI {
    public GuestBookedReservationsGUI(ReservationCatalog reservationCatalog) {
        super(reservationCatalog);

        JButton editButton = new JButton("Edit Reservation");
        JButton deleteButton = new JButton("Cancel Reservation");
        JButton checkInButton = new JButton("Check In");


        editButton.setFont(Style.defaultFont);
        deleteButton.setFont(Style.defaultFont);
        checkInButton.setFont(Style.defaultFont);


        deleteButton.addActionListener(new DeleteReservationAction(table, model));
        editButton.addActionListener(new EditReservationAction(table));
        checkInButton.addActionListener(new CheckInAction(table, model));


        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0)); // 1 row, 3 columns, with 10px horizontal gap
        buttonPanel.setBackground(Style.backgroundColor);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(checkInButton);


        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    @Override
    public void fillTableRows(Set<Reservation> unsortedReservations, DefaultTableModel model) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        int accountId = SessionManager.getInstance().getCurrentAccount().getId();
        ReservationController controller = new ReservationController(new ReservationJDBCDAO());
        Set<Reservation> guestReservations = controller.listReservations(accountId);

        for (Reservation reservation : guestReservations) {
            model.addRow(new Object[]{
                    accountId,
                    reservation.getReservationID(),
                    reservation.getRoomNumber(),
                    reservation.getFirstName(),
                    reservation.getLastName(),
                    reservation.getEmail(),
                    reservation.getNumberOfGuests(),
                    formatter.format(reservation.getStartDate()),
                    formatter.format(reservation.getEndDate()),
                    reservation.isCheckedIn()
            });
        }
    }
}

