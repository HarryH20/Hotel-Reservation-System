package org.bearluxury.UI;

import org.bearluxury.account.Role;
import org.bearluxury.controllers.ReservationController;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.reservation.ReservationJDBCDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ClerkBookedReservationsGUI extends BookedReservationsGUI{
    
    private Timer timer;
    public ClerkBookedReservationsGUI(ReservationCatalog reservationCatalog, Role role) {
        super(reservationCatalog, role);
        JButton editButton = new JButton("Edit Reservation");
        JButton deleteButton = new JButton("Delete Reservation");
        editButton.setFont(Style.defaultFont);
        deleteButton.setFont(Style.defaultFont);

        deleteButton.addActionListener(new deleteReservationAction());
        editButton.addActionListener(new editReservationAction());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Style.backgroundColor);
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottomPanel.add(editButton, BorderLayout.WEST);
        bottomPanel.add(deleteButton, BorderLayout.EAST);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private class editReservationAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){
                ReservationController controller = new ReservationController(new ReservationJDBCDAO());
                Optional<Reservation> opReservation = controller.getReservation(Integer.parseInt(table.getValueAt(selectedRow,1).toString()));
                Reservation reservation = opReservation.orElseThrow(() -> new NoSuchElementException("Reservation not found"));
                EditReservationPane pane = new EditReservationPane(reservation,model, table);
                pane.setVisible(true);

            }
            else{
                JOptionPane.showMessageDialog(null, "Please select a row first.");
            }


        }

    }
    private class deleteReservationAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){
                ReservationController controller = new ReservationController(new ReservationJDBCDAO());
                if(controller.deleteReservation(Integer.parseInt(table.getValueAt(selectedRow,1).toString()))){
                    model.removeRow(selectedRow);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Reservation not found");
                }

            }
            else{
                JOptionPane.showMessageDialog(null, "Please select a row first.");
            }

        }
    }

}
