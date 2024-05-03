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

/**
 * Represents a graphical user interface specifically designed for displaying booked
 * reservations by a guest. Extends the BookedReservationsGUI class.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 *
 */
public class GuestBookedReservationsGUI extends BookedReservationsGUI {
    /**
     * Constructs a GuestBookedReservationsGUI object.
     *
     * @param reservationCatalog The reservation catalog to be displayed.
     */
    public GuestBookedReservationsGUI(ReservationCatalog reservationCatalog) {
        super(reservationCatalog);

        JButton editButton = new JButton("Edit Reservation");
        JButton deleteButton = new JButton("Cancel Reservation");


        editButton.setFont(Style.defaultFont);
        deleteButton.setFont(Style.defaultFont);


        deleteButton.addActionListener(new DeleteReservationAction(table, model));
        editButton.addActionListener(new EditReservationAction(table));


        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0)); // 1 row, 3 columns, with 10px horizontal gap
        buttonPanel.setBackground(Style.backgroundColor);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);


        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }

    /**
     * Fills the table rows with guest reservations retrieved from the database.
     *
     * @param unsortedReservations The set of unsorted reservations.
     * @param model                The table model to fill with reservation data.
     */
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

