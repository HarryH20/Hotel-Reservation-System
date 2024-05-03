package org.bearluxury.UI.shopUI;

import org.bearluxury.UI.HotelManagementSystem;
import org.bearluxury.account.Role;
import org.bearluxury.controllers.ProductController;
import org.bearluxury.product.PRODUCT_TYPE;
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

/**
 * ProductTableGUI class represents a graphical user interface for displaying and managing
 * product information within a table.
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class ProductTableGUI extends JFrame {
    private final Color backgroundColor = new Color(232, 223, 185);
    private final Color tableHeaderColor = new Color(184, 134, 11);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.BOLD, 14);

    private DefaultTableModel model;
    private JTable table;

    /**
     * Constructs a ProductTableGUI object with the specified set of products.
     *
     * @param products The set of products to display in the table.
     */
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
                        int id = (int) table.getValueAt(selectedRow, 0);

                        table.setValueAt(newQuantity, selectedRow, 3);

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
        topPanel.add(addStockButton, BorderLayout.CENTER);


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
        topPanel.add(removeStockButton, BorderLayout.WEST);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int id = (int) model.getValueAt(selectedRow, 0);
                    model.removeRow(selectedRow);
                    try {
                        ProductController productController = new ProductController(new ProductJDBCDAO());
                        productController.deleteProduct(id);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        topPanel.add(deleteButton, BorderLayout.SOUTH);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        topPanel.add(addButton, BorderLayout.EAST);


        // Add the "Back" button
        topPanel.add(createBackButton(), BorderLayout.NORTH);
    }



    /**
     * Creates a table model for the product table.
     *
     * @return The created DefaultTableModel.
     */
    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Product ID", "Name", "Price", "Quantity", "Type"};
        return new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }



    /**
     * Adds a new product to the table upon user input.
     */
    private void addProduct() {
        JTextField nameField = new JTextField(10);
        JTextField priceField = new JTextField(5);
        JTextField quantityField = new JTextField(5);
        JComboBox<PRODUCT_TYPE> typeField = new JComboBox<>(PRODUCT_TYPE.values());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Type:"));
        panel.add(typeField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            PRODUCT_TYPE productType = (PRODUCT_TYPE) typeField.getSelectedItem();
            Product newProduct = new Product(name, price, quantity, productType);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{newProduct.getId(), newProduct.getName(), newProduct.getPrice(), newProduct.getQuantity(), newProduct.getProductType()});
            try {
                ProductController productController = new ProductController(new ProductJDBCDAO());
                productController.insertProduct(newProduct);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            refreshTable();
        }
    }

    /**
     * Refreshes the table with the latest data from the database.
     */
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear the table
        try {
            ProductController productController = new ProductController(new ProductJDBCDAO());
            Set<Product> products = productController.listProducts();
            fillTableRows(products, model);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a JTable with the specified table model and customizes its appearance.
     *
     * @param model The table model to be used for the JTable.
     * @return The created JTable.
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
     * Creates a JPanel with a BorderLayout and adds the specified JScrollPane to it.
     *
     * @param scrollPane The JScrollPane to be added to the panel.
     * @return The created JPanel.
     */
    private JPanel createPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Fills the table rows of the specified DefaultTableModel with product information
     * retrieved from the given set of products.
     *
     * @param products The set of products containing the data to fill the table rows.
     * @param model The DefaultTableModel to which the product information will be added.
     */
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

    /**
     * Creates a JButton labeled "Back" and sets up its ActionListener to handle the action of
     * returning to the previous page, based on the user's role.
     *
     * @return The created JButton configured with the "Back" label and ActionListener.
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
}

