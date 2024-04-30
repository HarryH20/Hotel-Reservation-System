package org.bearluxury.UI;

import org.bearluxury.account.*;
import org.bearluxury.controllers.ClerkAccountController;
import org.bearluxury.controllers.GuestAccountController;
import org.bearluxury.controllers.ReservationController;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationJDBCDAO;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GuestAccountClerkGUI extends JFrame {
    private GuestAccountController controller;
    private final Color backgroundColor = new Color(232, 223, 185);
    private final Color tableHeaderColor = new Color(184, 134, 11);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.BOLD, 14);

    public GuestAccountClerkGUI() {
        controller = new GuestAccountController(new GuestAccountJDBCDAO());

        setTitle("Account Information");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultTableModel model = createTableModel();

        JTable table = createTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = createPanel(scrollPane);

        getContentPane().setBackground(backgroundColor);
        fillTableRows(model);

        JButton backButton = createBackButton();
        JButton editButton = createEditButton(table);
        JButton deleteButton = createDeleteButton(table, model);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(editButton, BorderLayout.CENTER);
        topPanel.add(deleteButton, BorderLayout.EAST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
    }
    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Account ID","First Name", "Last Name", "Email", "Phone Number", "Password", "Role"};
        return new DefaultTableModel(columnNames, 0) {
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
                try {
                    System.out.println("This is my role: " + SessionManager.getInstance().getCurrentAccount().getRole());
                    if (SessionManager.getInstance().getCurrentAccount().getRole() == Role.GUEST) {
                        HotelManagementSystem.openGuestHomePage();
                    } else if (SessionManager.getInstance().getCurrentAccount().getRole() == Role.CLERK) {
                        HotelManagementSystem.openClerkHomePage();
                    } else if (SessionManager.getInstance().getCurrentAccount().getRole() == Role.ADMIN) {
                        HotelManagementSystem.openAdminHomePage();
                    } else {
                        throw new RuntimeException();
                    }
                } catch (RuntimeException exc) {
                    JOptionPane.showMessageDialog(null, "Invalid user info! Please contact admin.");
                }
            }
        });
        return backButton;
    }
    private JPanel createPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
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
    private void fillTableRows(DefaultTableModel model) {
        // Fetch account information from the database
        GuestAccountController controller = new GuestAccountController(new GuestAccountJDBCDAO());
        Set<Guest> accounts = controller.listAccounts();

        // Fill the table rows with account information
        accounts.stream()
                .filter(account -> account.getRole() == Role.GUEST)
                .forEach(account -> {
                    model.addRow(new Object[]{
                            String.valueOf(account.getId()),
                            account.getFirstName(),
                            account.getLastName(),
                            account.getEmail(),
                            account.getPhoneNumber(),
                            account.getPassword(),
                            account.getRole().toString(),

                    });

                });
    }
    private void editAccountDialog(int selectedRow, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String email = (String) model.getValueAt(selectedRow, 3); // Assuming email is at index 4

        Guest oldGuest = controller.getAccount(email).orElse(null);

        if (oldGuest != null) {
            // Create dialog for editing
            JDialog editDialog = new JDialog(this, "Edit Account", true);
            editDialog.setLayout(new GridLayout(9, 2));


            JTextField firstNameField = new JTextField(oldGuest.getFirstName());
            JTextField lastNameField = new JTextField(oldGuest.getLastName());
            JTextField emailField = new JTextField(oldGuest.getEmail());
            emailField.setEditable(false);
            emailField.setFocusable(false);

            MaskFormatter phoneFormatter = null;
            try {
                phoneFormatter = new MaskFormatter("###-###-####");
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            phoneFormatter.setPlaceholder(
                    String.valueOf(oldGuest.getPhoneNumber()).substring(0,3)+"-"
                            +String.valueOf(oldGuest.getPhoneNumber()).substring(3,6)+"-"
                            +String.valueOf(oldGuest.getPhoneNumber()).substring(6,10));
            JFormattedTextField phoneNumberField = new JFormattedTextField(phoneFormatter);
            JTextField passwordField = new JTextField(oldGuest.getPassword());

            JComboBox<Role> roleComboBox = new JComboBox<>(Role.values());
            roleComboBox.setSelectedItem(oldGuest.getRole());

            editDialog.add(new JLabel("First Name:"));
            editDialog.add(firstNameField);
            editDialog.add(new JLabel("Last Name:"));
            editDialog.add(lastNameField);
            editDialog.add(new JLabel("Email:"));
            editDialog.add(emailField);
            editDialog.add(new JLabel("Phone Number:"));
            editDialog.add(phoneNumberField);
            editDialog.add(new JLabel("Password:"));
            editDialog.add(passwordField);

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Update guest object with new data
                    oldGuest.setFirstName(firstNameField.getText());
                    oldGuest.setLastName(lastNameField.getText());
                    oldGuest.setEmail(emailField.getText());
                    oldGuest.setPhoneNumber(Long.parseLong(phoneNumberField.getText().replaceAll("-","")));
                    oldGuest.setPassword(passwordField.getText());



                    String editedPhoneNumber = String.valueOf(phoneNumberField.getValue());
                    String currentPhoneNumber = String.valueOf( model.getValueAt(selectedRow, 4));
                    if (!editedPhoneNumber.equals(currentPhoneNumber)) { // Check if email is edited

                        if (phoneNumberField.getValue() != null) {
                            ClerkAccountController controller = new ClerkAccountController(new ClerkAccountDAO());

                            // Check if phone is in use
                            for (Account account : controller.listAccounts()) {
                                if (account.getPhoneNumber() == Long
                                        .parseLong(String.valueOf(phoneNumberField.getValue())
                                                .replaceAll("-", ""))) {
                                    JOptionPane.showMessageDialog(null, "This Phone Number already in use.", "Warning", JOptionPane.WARNING_MESSAGE);
                                    editDialog.dispose();
                                    editAccountDialog(selectedRow,table);
                                    return;
                                }
                            }
                        }
                    }

                    if (firstNameField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please Fill in name Field.", "Warning", JOptionPane.WARNING_MESSAGE);
                        editDialog.dispose();
                        editAccountDialog(selectedRow,table);
                        return;
                    } else {
                        // Check if alphabetical
                        if (!firstNameField.getText().matches("[a-zA-Z]*$") || !lastNameField.getText().matches("[a-zA-Z]*$")) {
                            JOptionPane.showMessageDialog(null, "Name must be alphabetical.", "Warning", JOptionPane.WARNING_MESSAGE);
                            editDialog.dispose();
                            editAccountDialog(selectedRow,table);
                            return;
                        }

                    }

                    PasswordSpecifier passwordSpecifier = new PasswordSpecifier();
                    String editedPassword = passwordField.getText();
                    String currentPassword = (String) model.getValueAt(selectedRow, 5);
                    if (!editedPassword.equals(currentPassword)) { // Check if email is edited
                        if (passwordField.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Please Fill in password Field.", "Warning", JOptionPane.WARNING_MESSAGE);
                            editDialog.dispose();
                            editAccountDialog(selectedRow,table);
                            return;
                        }else{
                            // password does not meet specification, show error
                            if(!passwordSpecifier.checkPassword(passwordField.getText())) {
                                // if there is a problem with the password, it's not empty
                                JOptionPane.showMessageDialog(null, passwordSpecifier.getPasswordProblem(), "Warning", JOptionPane.WARNING_MESSAGE);
                                editDialog.dispose();
                                editAccountDialog(selectedRow,table);
                                return;
                            }
                        }
                    }

                    // Update database
                    controller.updateAccounts(oldGuest, email);

                    // Update row in table
                    model.setValueAt(oldGuest.getFirstName(), selectedRow, 1);
                    model.setValueAt(oldGuest.getLastName(), selectedRow, 2);
                    model.setValueAt(oldGuest.getEmail(), selectedRow, 3);
                    model.setValueAt(oldGuest.getPhoneNumber(), selectedRow, 4);
                    model.setValueAt(oldGuest.getPassword(), selectedRow, 5);

                    editDialog.dispose();
                }
            });

            editDialog.add(saveButton);
            editDialog.pack();
            editDialog.setLocationRelativeTo(this);
            editDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.");
        }
    }


    private JButton createEditButton(JTable table) {
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Open dialog for editing
                    editAccountDialog(selectedRow, table);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to edit.");
                }
            }
        });
        return editButton;
    }
    private JButton createDeleteButton(JTable table, DefaultTableModel model) {
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                ReservationController controller1 = new ReservationController(new ReservationJDBCDAO());
                if (selectedRow != -1) {
                    String accountNumberStr = (String) table.getValueAt(selectedRow, 0);
                    int accountNumber = Integer.parseInt(accountNumberStr); // Assuming account ID is at index 0

                    // Delete associated reservations first
                    Set<Reservation> reservations = controller1.listReservationsByAccountId(accountNumber);
                    for (Reservation reservation : reservations) {
                        controller1.deleteReservationByReservationId(reservation.getReservationID());
                    }

                    // Check if all reservations are deleted
                    boolean allReservationsDeleted = controller1.listReservationsByAccountId(accountNumber).isEmpty();
                    if (allReservationsDeleted) {
                        // Delete the account
                        boolean deleted = controller.deleteAccounts((String) table.getValueAt(selectedRow, 3)); // Assuming email is at index 5
                        if (deleted) {
                            // Delete selected row from table
                            model.removeRow(selectedRow);
                            JOptionPane.showMessageDialog(null, "Account and associated reservations deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to delete account and associated reservations.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete all reservations associated with the account.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                }
            }
        });
        return deleteButton;
    }


}
