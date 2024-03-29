package org.bearluxury;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookedReservationsGUI extends JFrame {
    private final Color backgroundColor = new Color(232, 223, 185);
    private final Color tableHeaderColor = new Color(184, 134, 11);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.BOLD, 16);

    public BookedReservationsGUI(ReservationCatalog reservationCatalog) {
        setTitle("Booked Reservations");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultTableModel model = createTableModel();

        JTable table = createTable(model);
        JScrollPane scrollPane = new JScrollPane(table);




        JPanel panel = createPanel(scrollPane);

        getContentPane().setBackground(backgroundColor);

        fillTableRows(reservationCatalog.getReservations(), model);

        JButton backButton = createBackButton();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.add(backButton, BorderLayout.WEST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Room ID", "First Name", "Last Name", "Email", "# Of Guests", "Start Date", "End Date"};
        return new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HotelHomePage window = new HotelHomePage();
                window.setVisible(true);
            }
        });
        return backButton;
    }


    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(Color.WHITE);
        table.getTableHeader().setBackground(tableHeaderColor);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(tableHeaderFont);
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setFont(tableFont);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        return table;
    }


    private JPanel createPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }


    private void fillTableRows(List<Reservation> reservations, DefaultTableModel model) {
        reservations.sort(Comparator.comparing(Reservation::getRoomNumber));
        reservations.sort(Comparator.comparing(Reservation::getStartDate));

        // format output dates
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");


        // room number,first name,last name,email,number of guests,start date,end date
        reservations
                .forEach(reservation -> model.addRow(new Object[]{
                        reservation.getRoomNumber(),
                        reservation.getFirstName(),
                        reservation.getLastName(),
                        reservation.getEmail(),
                        reservation.getNumberOfGuests(),
                        formatter.format(reservation.getStartDate()),
                        formatter.format(reservation.getEndDate())
                }));
    }

}
