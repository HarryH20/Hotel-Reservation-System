package org.bearluxury.UI;


import org.bearluxury.account.Account;
import org.bearluxury.account.ClerkAccountDAO;
import org.bearluxury.account.Role;
import org.bearluxury.controllers.ClerkAccountController;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ClerkAccountGUI extends JFrame {
    private final Color backgroundColor = new Color(232, 223, 185);
    private final Color tableHeaderColor = new Color(184, 134, 11);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.BOLD, 14);

    public ClerkAccountGUI(Set<Account> accounts) {
        setTitle("Account Information");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create table model and table
        DefaultTableModel model = createTableModel();
        JTable table = createTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel to hold the table
        JPanel panel = createPanel(scrollPane);

        // Set background color
        getContentPane().setBackground(backgroundColor);

        // Fill table with account data
        fillTableRows(accounts, model);

        // Buttons for editing and deleting
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClerkAccountController accountController = new ClerkAccountController(new ClerkAccountDAO());
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the email from the selected row
                    String email = (String) model.getValueAt(selectedRow, 3); // Assuming email is at index 4
                    // Delete the account from the database
                    boolean deleted = accountController.deleteAccounts(email);
                    if (deleted) {
                        // Remove the row from the table if deletion is successful
                        model.removeRow(selectedRow);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete account.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an account to edit.");
                }
            }
        });
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the data from the selected row
                    String firstName = (String) model.getValueAt(selectedRow, 1);
                    String lastName = (String) model.getValueAt(selectedRow, 2);
                    String email = (String) model.getValueAt(selectedRow, 3);
                    long phoneNumber = (long) model.getValueAt(selectedRow, 4);
                    String password = (String) model.getValueAt(selectedRow, 5);

                    JTextField firstNameField = new JTextField(firstName);
                    JTextField lastNameField = new JTextField(lastName);
                    JTextField emailField = new JTextField(email);
                    emailField.setEditable(false);
                    JTextField phoneNumberField = new JTextField(String.valueOf(phoneNumber));
                    JTextField passwordField = new JTextField(password);


                    JPanel editPanel = new JPanel(new GridLayout(8, 2));
                    editPanel.add(new JLabel("First Name:"));
                    editPanel.add(firstNameField);
                    editPanel.add(new JLabel("Last Name:"));
                    editPanel.add(lastNameField);
                    editPanel.add(new JLabel("Email:"));
                    editPanel.add(emailField);
                    editPanel.add(new JLabel("Phone Number:"));
                    editPanel.add(phoneNumberField);
                    editPanel.add(new JLabel("Password:"));
                    editPanel.add(passwordField);

                    int result = JOptionPane.showConfirmDialog(null, editPanel,
                            "Edit Account", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        // Update the row with edited data


                        // Check if the email is already in use
                        ClerkAccountController accountController = new ClerkAccountController(new ClerkAccountDAO());
                        String editedEmail = emailField.getText();
                        String currentEmail = (String) model.getValueAt(selectedRow, 3);
                        if (!editedEmail.equals(currentEmail)) { // Check if email is edited
                            Optional<Account> existingAccount = accountController.getAccount(editedEmail);
                            if (existingAccount.isPresent()) {
                                JOptionPane.showMessageDialog(null, "Email already in use. Please choose another one.", "Warning", JOptionPane.WARNING_MESSAGE);
                                return;
                            }
                        }
                        model.setValueAt(firstNameField.getText(), selectedRow, 1);
                        model.setValueAt(lastNameField.getText(), selectedRow, 2);
                        model.setValueAt(emailField.getText(), selectedRow, 3);
                        model.setValueAt(Long.parseLong(phoneNumberField.getText()), selectedRow, 4);
                        model.setValueAt(passwordField.getText(), selectedRow, 5);

                        // Create an Account object with updated information
                        Account updatedAccount = new Account(
                                firstNameField.getText(),
                                lastNameField.getText(),
                                emailField.getText(),
                                Long.parseLong(phoneNumberField.getText()),
                                passwordField.getText(),
                                Role.CLERK
                        );

                        // Update the account in the database
                        accountController.updateAccounts(updatedAccount, currentEmail);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an account to edit.");
                }
            }
        });

        // Panel for buttons at the top
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topButtonPanel.setBackground(backgroundColor);
        topButtonPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        topButtonPanel.add(deleteButton);
        topButtonPanel.add(editButton);

        // Back button
        JButton backButton = createBackButton();

        // Panel for back button at the top
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        topPanel.add(backButton, BorderLayout.WEST);

        // Set layout and add components to the content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(topButtonPanel, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.SOUTH);
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

    private void fillTableRows(Set<Account> accounts, DefaultTableModel model) {
        // Fill the table rows with account information
        accounts.stream().filter(account -> account.getRole() == Role.CLERK).
                collect(Collectors.toSet()).
                forEach(account -> model.addRow(new Object[]{
                String.valueOf(account.getId()),
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                account.getPhoneNumber(),
                        account.getPassword(),
                account.getRole().toString()
        }));
    }
}

