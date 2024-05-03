package org.bearluxury.UI;
import org.bearluxury.account.Role;
import org.bearluxury.reservation.ReservationCatalog;
import org.bearluxury.room.Room;
import org.bearluxury.room.RoomCatalog;
import org.bearluxury.state.SessionManager;

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
/**
 * Represents a GUI for displaying available rooms.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 *
 */
public class AvaliableRoomsGUI extends JFrame {
    /**
     * Background color of the GUI.
     */
    private final Color backgroundColor = new Color(232, 223, 185);
    /**
     * Color of the table header.
     */
    private final Color tableHeaderColor = new Color(184, 134, 11);
    /**
     * Font for the table header.
     */
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    /**
     * Font for the table content.
     */
    private final Font tableFont = new Font("Arial", Font.BOLD, 16);
    /**
     * Constructs a new AvaliableRoomsGUI.
     *
     * @param roomCatalog       The catalog of rooms.
     * @param beds              The number of beds required.
     * @param checkIn           The check-in date.
     * @param checkOut          The check-out date.
     * @param reservationCatalog The catalog of reservations.
     */

    public AvaliableRoomsGUI(RoomCatalog roomCatalog, int beds, LocalDate checkIn, LocalDate checkOut,
                             ReservationCatalog reservationCatalog) {
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

        fillTableRows(roomCatalog.getRooms(), model, beds, reservationCatalog, checkIn, checkOut);

        JButton backButton = createBackButton();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.add(backButton, BorderLayout.WEST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonWrapperPanel, BorderLayout.SOUTH);
    }

    /**
     * Creates the table model for the GUI.
     *
     * @return The table model.
     */
    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Room ID", "Room Type", "Price", "Quality", "# Of Beds", "Bed Type", "Smoking Allowed"};
        return new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
    /**
     * Creates the back button for the GUI.
     *
     * @return The back button.
     */
    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    System.out.println("This is my role: " + SessionManager.getInstance().getCurrentAccount().getRole());
                    if (SessionManager.getInstance().getCurrentAccount().getRole() == Role.GUEST) {
                        HotelManagementSystem.openGuestHomePage();
                    }
                    else if (SessionManager.getInstance().getCurrentAccount().getRole() == Role.CLERK) {
                        HotelManagementSystem.openClerkHomePage();
                    }
                    else if (SessionManager.getInstance().getCurrentAccount().getRole() == Role.ADMIN) {
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

    /**
     * Creates the table for the GUI.
     *
     * @param model The table model.
     * @return The created table.
     */
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

    /**
     * Creates the reservation button for the GUI.
     *
     * @param table     The table to which the button is associated.
     * @param checkIn   The check-in date.
     * @param checkOut  The check-out date.
     * @param model     The table model.
     * @return The reservation button.
     */
    private JButton createReservationButton(JTable table, LocalDate checkIn, LocalDate checkOut, DefaultTableModel model) {
        JButton reservationButton = new JButton("Make Reservation");
        reservationButton.setPreferredSize(new Dimension(200, 50));
        reservationButton.setMargin(new Insets(10, 20, 10, 20));
        reservationButton.setFont(new Font("Arial", Font.BOLD, 15));
        reservationButton.setForeground(Color.BLACK);
        reservationButton.addActionListener(new ReservationFormOpener(table, checkIn, checkOut, model));
        return reservationButton;
    }

    /**
     * Creates the panel to hold the table.
     *
     * @param scrollPane The scroll pane containing the table.
     * @return The panel containing the scroll pane.
     */
    private JPanel createPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Creates the panel to hold the reservation button.
     *
     * @param reservationButton The reservation button.
     * @return The panel containing the reservation button.
     */
    private JPanel createButtonWrapperPanel(JButton reservationButton) {
        JPanel buttonWrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapperPanel.setBackground(backgroundColor);
        buttonWrapperPanel.add(reservationButton);
        return buttonWrapperPanel;
    }

    /**
     * Fills the table rows with available rooms based on the specified criteria.
     *
     * @param unsortedRooms      The list of unsorted rooms.
     * @param model              The table model.
     * @param beds               The number of beds required.
     * @param reservationCatalog The catalog of reservations.
     * @param checkIn            The check-in date.
     * @param checkOut           The check-out date.
     */
    private void fillTableRows(Set<Room> unsortedRooms, DefaultTableModel model, int beds,
                               ReservationCatalog reservationCatalog, LocalDate checkIn, LocalDate checkOut) {
        int maxBeds =  unsortedRooms.stream().mapToInt(Room::getNumberOfBeds).max().orElseThrow();
        List<Room> rooms = unsortedRooms.stream().
                sorted(Comparator.comparing(Room::getNumberOfBeds).
                thenComparing(Room::getRoomNumber)).
                toList();
        try {
            if(beds > maxBeds){
                throw new IllegalArgumentException();

            }
            rooms.stream()
                    .filter(room -> room.getNumberOfBeds() >= beds)
                    .filter(room -> reservationCatalog.isAvailableDate(room,checkIn,checkOut))
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
            fillTableRows(unsortedRooms,model,1, reservationCatalog, checkIn, checkOut);

        }
    }

    /**
     * ActionListener implementation to handle reservation button clicks.
     */
    private static class ReservationFormOpener implements ActionListener {
        private final JTable table;

        private DefaultTableModel model;
        LocalDate checkIn;

        LocalDate checkOut;


        /**
         * Constructs a new ReservationFormOpener.
         *
         * @param table     The table associated with the reservation button.
         * @param checkIn   The check-in date.
         * @param checkOut  The check-out date.
         * @param model     The table model.
         */
        private ReservationFormOpener(JTable table, LocalDate checkIn, LocalDate checkOut, DefaultTableModel model) {
            this.table = table;
            this.model = model;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
        }

        /**
         * Handles the action event triggered by the reservation button.
         *
         * @param e The action event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int roomId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                if(SessionManager.getInstance().getCurrentAccount().getRole() == Role.CLERK) {
                    openReservationFormClerk(roomId, checkIn, checkOut);
                }
                else{
                    openReservationFormGuest(roomId, checkIn, checkOut);
                }
                model.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row first.");
            }
        }

        /**
         * Opens the reservation form for a guest.
         *
         * @param roomID    The ID of the room for which reservation is made.
         * @param checkIn   The check-in date.
         * @param checkOut  The check-out date.
         */
        private static void openReservationFormGuest(int roomID, LocalDate checkIn, LocalDate checkOut) {
            ReservationPaneGuest pane = new ReservationPaneGuest(roomID, checkIn, checkOut);
            pane.setVisible(true);
        }

        /**
         * Opens the reservation form for a clerk.
         *
         * @param roomID    The ID of the room for which reservation is made.
         * @param checkIn   The check-in date.
         * @param checkOut  The check-out date.
         */
        private static void openReservationFormClerk(int roomID, LocalDate checkIn, LocalDate checkOut) {
            ReservationPaneClerk pane = new ReservationPaneClerk(roomID, checkIn, checkOut);
            pane.setVisible(true);
        }
    }
}
