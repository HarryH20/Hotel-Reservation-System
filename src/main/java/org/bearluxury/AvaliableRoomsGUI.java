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


        List<Room> rooms = roomCatalog.getRooms();
        DefaultTableModel model = fillColumns(new DefaultTableModel(),columnNames);
        fillRows(rooms,model);

        JTable table = new JTable(model){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setBackground(Color.WHITE);
        table.getTableHeader().setBackground(tableHeaderColor);
        table.getTableHeader().setForeground(Color.BLACK);
        table.getTableHeader().setFont(tableHeaderFont);
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);


        table.setFont(tableFont);
        table.setRowHeight(30);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setForeground(Color.BLACK);
        table.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton reservationButton = new JButton("Make Reservation");
        reservationButton.setPreferredSize(new Dimension(200, 50));
        reservationButton.setMargin(new Insets(10, 20, 10, 20));
        reservationButton.setFont(new Font("Arial", Font.BOLD, 20));
        reservationButton.setForeground(Color.BLACK);

        JPanel buttonWrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapperPanel.setBackground(backgroundColor);
        buttonWrapperPanel.add(reservationButton);

        getContentPane().setBackground(backgroundColor);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonWrapperPanel, BorderLayout.SOUTH);


    }
    private void fillRows(List<Room> rooms, DefaultTableModel model){
        for (Room room : rooms) {
            model.addRow(new Object[]{
                    room.getRoomType(),
                    room.getPrice(),
                    room.getQualityLevel(),
                    room.getNumberOfBeds(),
                    room.getBed(),
                    room.isCanSmoke() ? "Yes" : "No"

            });
        }

    }
    private DefaultTableModel fillColumns(DefaultTableModel model, String[] columns){
        model = new DefaultTableModel(columns,0);
        return model;
    }
}
