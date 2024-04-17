package org.bearluxury.UI;

import org.bearluxury.account.Role;
import org.bearluxury.controllers.ReservationController;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.reservation.ReservationJDBCDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClerkBookedReservationsGUI extends BookedReservationsGUI{
    public ClerkBookedReservationsGUI(ReservationCatalog reservationCatalog, Role role) {
        super(reservationCatalog, role);
        JButton editButton = new JButton("Edit Reservation");
        JButton deleteButton = new JButton("Delete Reservation");
        editButton.setFont(Style.defaultFont);
        deleteButton.setFont(Style.defaultFont);
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

            }


        }
    }
    private class deleteReservationAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if(selectedRow != -1){
                ReservationController controller = new ReservationController(new ReservationJDBCDAO());
                controller.deleteReservation(Integer.parseInt(table.getValueAt(selectedRow,0).toString()));
                model.removeRow(selectedRow);
            }
            else{
                JOptionPane.showMessageDialog(null, "Please select a row first.");
            }

        }
    }

}
