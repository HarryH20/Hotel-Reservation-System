package org.bearluxury.UI;


import org.bearluxury.account.*;
import org.bearluxury.controllers.ClerkAccountController;
import org.bearluxury.controllers.GuestAccountController;
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

/**
 * Represents a graphical user interface for managing clerk accounts.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 *
 */
public class ClerkAccountGUI extends JFrame {
    private final Color backgroundColor = new Color(232, 223, 185);
    private final Color tableHeaderColor = new Color(184, 134, 11);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.BOLD, 14);

    /**
     * Constructs a new ClerkAccountGUI.
     *
     * @param accounts The set of clerk accounts.
     */
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

                    String email = (String) model.getValueAt(selectedRow, 3);
                    boolean deleted = accountController.deleteAccounts(email);
                    if (deleted) {
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
                openEditDialog(table,model);
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


    /**
     * Creates the table model for the GUI.
     *
     * @return The table model.
     */
    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Account ID","First Name", "Last Name", "Email", "Phone Number", "Password", "Role"};
        return new DefaultTableModel(columnNames, 0) {
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
     * Creates the panel to hold the table.
     *
     * @param scrollPane The scroll pane containing the table.
     * @return The panel containing the table.
     */
    private JPanel createPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Fills the table rows with clerk account information.
     *
     * @param accounts The set of clerk accounts.
     * @param model    The table model.
     */
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
    /**
     * Opens a dialog for editing clerk account information.
     *
     * @param table The table displaying clerk accounts.
     * @param model The table model.
     */
    public void openEditDialog(JTable table,DefaultTableModel model){

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
            emailField.setFocusable(false);

            MaskFormatter phoneFormatter = null;
            try {
                phoneFormatter = new MaskFormatter("###-###-####");
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
            phoneFormatter.setPlaceholder(String.valueOf(phoneNumber).substring(0,3)+"-"+String.valueOf(phoneNumber).substring(3,6)+"-"+String.valueOf(phoneNumber).substring(6,10));
            JFormattedTextField phoneNumberField = new JFormattedTextField(phoneFormatter);
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

            JOptionPane p = new JOptionPane();
            int result = p.showOptionDialog(null, editPanel, "Edit Account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);


            if (result == JOptionPane.OK_OPTION) {
                // Update the row with edited data


                // Check if the email is already in use
                ClerkAccountController accountController = new ClerkAccountController(new ClerkAccountDAO());
                String editedEmail = emailField.getText();
                String currentEmail = (String) model.getValueAt(selectedRow, 3);


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
                                openEditDialog(table,model);
                                return;
                            }
                        }
                    }
                }

                if (firstNameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Fill in name Field.", "Warning", JOptionPane.WARNING_MESSAGE);
                    openEditDialog(table,model);
                    return;
                } else {
                    // Check if alphabetical
                    if (!firstNameField.getText().matches("[a-zA-Z]*$") || !lastNameField.getText().matches("[a-zA-Z]*$")) {
                        JOptionPane.showMessageDialog(null, "Name must be alphabetical.", "Warning", JOptionPane.WARNING_MESSAGE);
                        openEditDialog(table, model);
                        return;
                    }

                }

                PasswordSpecifier passwordSpecifier = new PasswordSpecifier();
                String editedPassword = passwordField.getText();
                String currentPassword = (String) model.getValueAt(selectedRow, 5);
                if (!editedPassword.equals(currentPassword)) { // Check if email is edited
                    if (passwordField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Please Fill in password Field.", "Warning", JOptionPane.WARNING_MESSAGE);
                        openEditDialog(table,model);
                        return;
                    }else{
                        // password does not meet specification, show error
                        if(!passwordSpecifier.checkPassword(passwordField.getText())) {
                            // if there is a problem with the password, it's not empty
                            JOptionPane.showMessageDialog(null, passwordSpecifier.getPasswordProblem(), "Warning", JOptionPane.WARNING_MESSAGE);
                            openEditDialog(table,model);
                            return;
                        }
                    }
                }

                model.setValueAt(firstNameField.getText(), selectedRow, 1);
                model.setValueAt(lastNameField.getText(), selectedRow, 2);
                model.setValueAt(emailField.getText(), selectedRow, 3);
                model.setValueAt(Long.parseLong(phoneNumberField.getText().replaceAll("-","")), selectedRow, 4);
                model.setValueAt(passwordField.getText(), selectedRow, 5);

                // Create an Account object with updated information
                Account updatedAccount = new Account(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        Long.parseLong(phoneNumberField.getText().replaceAll("-","")),
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

}

