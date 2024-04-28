package org.bearluxury.UI;

import org.bearluxury.Billing.SaleJDBCDAO;
import org.bearluxury.account.CreditCard;
import org.bearluxury.account.GuestAccountJDBCDAO;
import org.bearluxury.controllers.GuestAccountController;
import org.bearluxury.controllers.ReservationController;
import org.bearluxury.controllers.RoomController;
import org.bearluxury.controllers.SaleController;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.room.RoomJDBCDAO;
import org.bearluxury.shop.Sale;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class ClerkBookedReservationsGUI extends BookedReservationsGUI {


    public ClerkBookedReservationsGUI(ReservationCatalog reservationCatalog) {
        super(reservationCatalog);

        JButton editButton = new JButton("Edit Reservation");
        JButton deleteButton = new JButton("Delete Reservation");
        JButton checkInButton = new JButton("Check In");
        JButton checkOutButton = new JButton("Check Out");


        editButton.setFont(Style.defaultFont);
        deleteButton.setFont(Style.defaultFont);
        checkInButton.setFont(Style.defaultFont);


        deleteButton.addActionListener(new DeleteReservationAction(table, model));
        editButton.addActionListener(new EditReservationAction(table));
        checkInButton.addActionListener(new CheckInAction(table, model));
        checkOutButton.addActionListener(new CheckOutAction(table, model));


        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 0)); // 1 row, 3 columns, with 10px horizontal gap
        buttonPanel.setBackground(Style.backgroundColor);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(checkInButton);
        buttonPanel.add(checkOutButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}

class EditReservationAction implements ActionListener {
    private JTable table;

    public EditReservationAction(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            ReservationController controller = new ReservationController(new ReservationJDBCDAO());
            Optional<Reservation> opReservation = controller.getReservationByReservationId(Integer.parseInt(table.getValueAt(selectedRow, 1).toString()));
            Reservation reservation = opReservation.orElseThrow(() -> new NoSuchElementException("Reservation not found"));
            EditReservationPane pane = new EditReservationPane(reservation, (DefaultTableModel) table.getModel(), table);
            pane.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a row first.");
        }
    }
}

class DeleteReservationAction implements ActionListener {
    private JTable table;
    private DefaultTableModel model;

    public DeleteReservationAction(JTable table, DefaultTableModel model) {
        this.table = table;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            ReservationController controller = new ReservationController(new ReservationJDBCDAO());
            SaleController saleController = new SaleController(new SaleJDBCDAO());
            RoomController roomController = null;
            try {
                roomController = new RoomController(new RoomJDBCDAO());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            int roomNumber = roomController.getRoom(
                    controller.getReservationByReservationId(
                            Integer.parseInt(table.getValueAt(
                                    selectedRow, 1).toString())).
                            orElseThrow().getRoomNumber()).
                    orElseThrow().
                    getRoomNumber();

            Reservation res = controller.
                    getReservationByReservationId(Integer.parseInt(table.getValueAt(selectedRow, 1).toString())).
                    orElseThrow(() -> new NoSuchElementException("Reservation Doesn't exist"));

            long differenceMillis = new Date().getTime() - res.getEndDate().getTime();
            long daysApart = differenceMillis / (1000 * 60 * 60 * 24);

            if (controller.deleteReservationByReservationId(Integer.parseInt(table.getValueAt(selectedRow, 1).toString()))) {
                Set<Sale> sales = saleController.listSale(Integer.parseInt(table.getValueAt(selectedRow, 0).toString()));
                for(Sale sale: sales){
                    if(table.getValueAt(selectedRow, 2).toString().equals(sale.getProductName())){
                        if(daysApart > 2){
                            GuestAccountController guestAccountController = new GuestAccountController(new GuestAccountJDBCDAO());
                            CreditCard card = guestAccountController.getAccount(table.getValueAt(selectedRow,5).toString()).orElseThrow().getCreditCard();
                            card.chargeCard(0.8 * sale.getPrice() * sale.getQuantity());
                            JOptionPane.showMessageDialog(null,"Card charged for late cancellation!");
                        }
                        saleController.deleteSaleBySaleId(sale.getSaleId());
                        JOptionPane.showMessageDialog(null,"Reservation Deleted!");

                    }

                }

                model.removeRow(selectedRow);
            }
            else {
                JOptionPane.showMessageDialog(null, "Reservation not found");
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Please select a row first.");
        }
    }
}
class CheckInAction implements ActionListener{

    private JTable table;
    private DefaultTableModel model;

    public CheckInAction(JTable table, DefaultTableModel model) {
        this.table = table;
        this.model = model;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        ReservationController controller = new ReservationController(new ReservationJDBCDAO());
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        if(selectedRow != -1) {
            Reservation res = controller.
                    getReservationByReservationId(Integer.parseInt(table.getValueAt(selectedRow, 1).toString())).
                    orElseThrow(() -> new NoSuchElementException("Reservation Doesn't exist"));

            LocalDate currentDate = LocalDate.now();

            LocalDate reservationStartDate = Instant.ofEpochMilli(res.getStartDate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if(reservationStartDate.equals(currentDate)) {
                if(res.isCheckedIn()){
                    JOptionPane.showMessageDialog(null, "You are already checked in!");
                }

                else{
                    res.setCheckedIn(true);
                    controller.updateReservationByReservationId(res, res.getReservationID());
                    model.removeRow(selectedRow);
                    model.addRow(new Object[]{
                            new GuestAccountController(new GuestAccountJDBCDAO()).getAccount(res.getEmail()).
                                    orElseThrow(()-> new NoSuchElementException("No active accounts with reservations")).
                                    getId(),
                            res.getReservationID(),
                            res.getRoomNumber(),
                            res.getFirstName(),
                            res.getLastName(),
                            res.getEmail(),
                            res.getNumberOfGuests(),
                            formatter.format(res.getStartDate()),
                            formatter.format(res.getEndDate()),
                            res.isCheckedIn()
                    });

                    JOptionPane.showMessageDialog(null,  res.getFirstName() + " " +
                            res.getLastName() + " has successfully checked in");
                }

            }
            else if(reservationStartDate.isAfter(currentDate)){
                JOptionPane.showMessageDialog(null, "Check-in date is " + formatter.format(res.getStartDate())+ ". Please check in on that date!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Your check-in window has passed");
            }

        }


    }
}
class CheckOutAction implements ActionListener{
    private JTable table;
    private DefaultTableModel model;

    public CheckOutAction(JTable table, DefaultTableModel model) {
        this.table = table;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if(selectedRow != -1){
            SaleController controller = new SaleController(new SaleJDBCDAO());
            ReservationController resController = new ReservationController(new ReservationJDBCDAO());
            Reservation reservation= resController.
                    getReservationByReservationId(Integer.parseInt(table.getValueAt(selectedRow,1).
                            toString())).orElseThrow(() -> new NoSuchElementException("Reservation not found"));
            Set<Sale> unpaidSales = controller.listSale();

            LocalDate currentDate = LocalDate.now();

            LocalDate reservationEndDate = Instant.ofEpochMilli(reservation.getEndDate().getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if(currentDate.equals(reservationEndDate)) {
                if (!(unpaidSales.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "Bill not settled." +
                            "Please navigate to your bill to pay!");
                }
                else if(unpaidSales.isEmpty() && reservation.isCheckedIn()){
                    resController.deleteReservationByReservationId(Integer.parseInt(table.getValueAt(selectedRow,1).
                            toString()));
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "guest has been Checked Out!");
                }
            }
            else if(currentDate.isAfter(reservationEndDate)){
                long differenceMillis = new Date().getTime() - reservation.getEndDate().getTime();
                long daysApart = differenceMillis / (1000 * 60 * 60 * 24);

                Sale sale = new Sale(reservation.getEndDate(), "Late Check-Out", 50, (int)daysApart);
                sale.setAccountId(reservation.getId());
                controller.insertSale(sale);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(reservation.getEndDate());
                calendar.add(Calendar.DATE, (int) daysApart);

                reservation.setEndDate(calendar.getTime());
                resController.updateReservationByReservationId(reservation, Integer.parseInt(table.getValueAt(selectedRow,1).
                        toString()));
                JOptionPane.showMessageDialog(null, "You are late to check out!");

            }
            else{
                JOptionPane.showMessageDialog(null, "You can't check out yet!");
            }

        }
    }

}
