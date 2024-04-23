package org.bearluxury.UI;

import org.bearluxury.account.AccountJDBCDAO;
import org.bearluxury.controllers.AccountController;
import org.bearluxury.controllers.ReservationController;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.state.SessionManager;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Set;

public class GuestBookedReservationsGUI extends BookedReservationsGUI{
    public GuestBookedReservationsGUI(ReservationCatalog reservationCatalog) {
        super(reservationCatalog);

    }

    @Override
    public void fillTableRows(Set<Reservation> unsortedReservations, DefaultTableModel model) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
        int acctId = SessionManager.getInstance().getCurrentAccount().getId();
        ReservationController controller = new ReservationController(new ReservationJDBCDAO());
        for(Reservation reservation:controller.listReservations()) {
            if (reservation.getId() == acctId) {
                model.addRow(new Object[]{
                        acctId,
                        reservation.getId(),
                        reservation.getRoomNumber(),
                        reservation.getFirstName(),
                        reservation.getLastName(),
                        reservation.getEmail(),
                        reservation.getNumberOfGuests(),
                        formatter.format(reservation.getStartDate()),
                        formatter.format(reservation.getEndDate())
                });
            }
        }


    }
}
