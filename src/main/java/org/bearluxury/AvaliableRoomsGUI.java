package org.bearluxury;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliableRoomsGUI extends JFrame {

    Color backgroundColor = new Color(232,223,185,255);
    Color tableHeaderColor = new Color(184, 134, 11);
    Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    Font tableFont = new Font("Arial", Font.BOLD,16);
    AvaliableRoomsGUI(RoomCatalog roomCatalog) {
        setTitle("Room Catalog");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table model with data
        String[] columnNames = {"Room Type", "Price", "Quality", "# Of Beds", "Bed Type", "Smoking Allowed"};
        Object[][] data;


        List<Room> rooms = roomCatalog.getRooms();

        DefaultTableModel model = new DefaultTableModel(columnNames,0);
        for (Room room : rooms) {
            model.addRow(new Object[]{
//                    room.getRoomNumber(),
//                    room.getPrice(),
//                    room.isCanSmoke() ? "Yes" : "No",
//                    room.getRoomType(),
//                    room.getBed(),
//                    room.getQualityLevel(),
//                    room.getNumberOfBeds()
            });
        }

        JTable table = new JTable(model);
        table.setBackground(Color.BLACK);
        table.getTableHeader().setBackground(tableHeaderColor);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(tableHeaderFont);
        table.setGridColor(Color.WHITE);
        table.setFillsViewportHeight(true);


        table.setFont(tableFont);
        table.setRowHeight(30);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setForeground(Color.WHITE);
        table.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton reservationButton = new JButton("Make Reservation");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.add(reservationButton);

        getContentPane().setBackground(backgroundColor);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);


    }
}
