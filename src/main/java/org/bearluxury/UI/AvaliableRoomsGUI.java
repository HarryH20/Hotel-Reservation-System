package org.bearluxury.UI;
import org.bearluxury.account.Role;
import org.bearluxury.room.Room;
import org.bearluxury.room.RoomCatalog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class AvaliableRoomsGUI extends JFrame {

    private final Color backgroundColor = new Color(232, 223, 185);
    private final Color tableHeaderColor = new Color(184, 134, 11);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.BOLD, 16);

    public AvaliableRoomsGUI(RoomCatalog roomCatalog, int beds, LocalDate checkIn, LocalDate checkOut, Role role) {
        setTitle("Room Catalog");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultTableModel model = createTableModel();

        JTable table = createTable(model);
        JScrollPane scrollPane = new JScrollPane(table);


        JButton reservationButton = createReservationButton(table, checkIn,checkOut, model);

        JPanel panel = createPanel(scrollPane);
        JPanel buttonWrapperPanel = createButtonWrapperPanel(reservationButton);

        getContentPane().setBackground(backgroundColor);

        fillTableRows(roomCatalog.getRooms(), model, beds);

        JButton backButton = createBackButton(role);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.add(backButton, BorderLayout.WEST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonWrapperPanel, BorderLayout.SOUTH);
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Room ID", "Room Type", "Price", "Quality", "# Of Beds", "Bed Type", "Smoking Allowed"};
        return new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
    private JButton createBackButton(Role role) {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    System.out.println("This is my role: " + role);
                    if (role == Role.GUEST) {
                        HotelManagementSystem.openGuestHomePage();
                    }
                    else if (role == Role.CLERK) {
                        HotelManagementSystem.openClerkHomePage();
                    }
                    else if (role == Role.ADMIN) {
                        HotelManagementSystem.openAdminHomePage();
                    }
                    else{
                        throw new RuntimeException();
                    }
                }catch(RuntimeException exc){
                    JOptionPane.showMessageDialog(null,"Invalid user info! please contact admin.");
                }

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

    private JButton createReservationButton(JTable table, LocalDate checkIn, LocalDate checkOut, DefaultTableModel model) {
        JButton reservationButton = new JButton("Make Reservation");
        reservationButton.setPreferredSize(new Dimension(200, 50));
        reservationButton.setMargin(new Insets(10, 20, 10, 20));
        reservationButton.setFont(new Font("Arial", Font.BOLD, 15));
        reservationButton.setForeground(Color.BLACK);
        reservationButton.addActionListener(new ReservationFormOpener(table, checkIn, checkOut, model));
        return reservationButton;
    }

    private JPanel createPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createButtonWrapperPanel(JButton reservationButton) {
        JPanel buttonWrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapperPanel.setBackground(backgroundColor);
        buttonWrapperPanel.add(reservationButton);
        return buttonWrapperPanel;
    }

    private void fillTableRows(Set<Room> unsortedRooms, DefaultTableModel model, int beds) {
        int maxBeds =  unsortedRooms.stream().mapToInt(Room::getNumberOfBeds).max().orElseThrow();
        List<Room> rooms = unsortedRooms.stream().
                sorted(Comparator.comparing(Room::getNumberOfBeds).
                thenComparing(Room::getRoomNumber)).
                collect(Collectors.toList());
        try {
            if(beds > maxBeds){
                throw new IllegalArgumentException();

            }
            rooms.stream()
                    .filter(room -> room.getNumberOfBeds() >= beds)
                    .forEach(room -> model.addRow(new Object[]{
                            room.getRoomNumber(),
                            room.getRoomType().toString(),
                            room.getPrice(),
                            room.getQualityLevel().toString(),
                            room.getNumberOfBeds(),
                            room.getBed().toString(),
                            room.isCanSmoke() ? "Yes" : "No"
                    }));
        }catch (IllegalArgumentException exc){
            JOptionPane.showMessageDialog(null,"Error: beds must be less than " +
                    + maxBeds + ". \nShowing entire catalog!" );
            fillTableRows(unsortedRooms,model,1);

        }
    }

    private static class ReservationFormOpener implements ActionListener {
        private final JTable table;

        private DefaultTableModel model;
        LocalDate checkIn;

        LocalDate checkOut;


        private ReservationFormOpener(JTable table, LocalDate checkIn, LocalDate checkOut, DefaultTableModel model) {
            this.table = table;
            this.model = model;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int roomId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                openReservationForm(roomId, checkIn, checkOut);
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row first.");
            }
        }

        private static void openReservationForm(int roomID, LocalDate checkIn, LocalDate checkOut) {
            ReservationPane pane = new ReservationPane(roomID, checkIn, checkOut);
            pane.setVisible(true);
        }
    }
}
