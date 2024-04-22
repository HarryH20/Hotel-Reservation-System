package org.bearluxury.UI;

import org.bearluxury.account.Role;
import org.bearluxury.controllers.ProductController;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductJDBCDAO;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Set;

public class ProductTableGUI extends JFrame {
    private final Color backgroundColor = new Color(232, 223, 185);
    private final Color tableHeaderColor = new Color(184, 134, 11);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.BOLD, 14);

    private DefaultTableModel model;
    private JTable table;

    public ProductTableGUI(Set<Product> products) {
        setTitle("Product Information");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = createTableModel();

        table = createTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = createPanel(scrollPane);

        getContentPane().setBackground(backgroundColor);

        fillTableRows(products, model);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);

        // Add the "Add Stock" button
        JButton addStockButton = new JButton("Add Stock");
        addStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String amountStr = JOptionPane.showInputDialog(null, "Enter the amount to add:");
                    if (amountStr != null && !amountStr.isEmpty()) {
                        int amount = Integer.parseInt(amountStr);
                        int currentQuantity = (int) table.getValueAt(selectedRow, 3);
                        int newQuantity = currentQuantity + amount;
                        int id = (int) table.getValueAt(selectedRow, 0); // Assuming ID is in the first column

                        // Update GUI
                        table.setValueAt(newQuantity, selectedRow, 3);

                        // Update Database
                        try {
                            ProductController productController = new ProductController(new ProductJDBCDAO());
                            productController.addStock(id, amount);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to add stock to.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        topPanel.add(addStockButton, BorderLayout.WEST);


        // Add the "Remove Stock" button
        JButton removeStockButton = new JButton("Remove Stock");
        removeStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String amountStr = JOptionPane.showInputDialog(null, "Enter the amount to remove:");
                    if (amountStr != null && !amountStr.isEmpty()) {
                        int amount = Integer.parseInt(amountStr);
                        int currentQuantity = (int) table.getValueAt(selectedRow, 3);

                        if (currentQuantity >= amount) {
                            int newQuantity = currentQuantity - amount;
                            int id = (int) table.getValueAt(selectedRow, 0);

                            // Update GUI
                            table.setValueAt(newQuantity, selectedRow, 3);

                            // Update Database
                            try {
                                ProductController productController = new ProductController(new ProductJDBCDAO());
                                productController.removeStock(id, amount);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Not enough stock to remove!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to remove stock from.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        topPanel.add(removeStockButton, BorderLayout.CENTER);


        // Add the "Back" button
        topPanel.add(createBackButton(), BorderLayout.EAST);
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Product ID", "Name", "Price", "Quantity", "Type"};
        return new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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

    private void fillTableRows(Set<Product> products, DefaultTableModel model) {
        // Fill the table rows with product information
        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getProductType().toString()
            });
        }
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
}

