package org.bearluxury.UI;

import org.bearluxury.Billing.SaleJDBCDAO;
import org.bearluxury.account.Role;
import org.bearluxury.shop.Sale;
import org.bearluxury.controllers.SaleController;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Set;

public class BillingPage extends JFrame {
    private final Color backgroundColor = new Color(232, 223, 185);
    private final Font headerFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.PLAIN, 14);

    private JTable saleTable;

    public BillingPage() {
        setTitle("Billing Page");
        setSize(1000, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(backgroundColor);

        createComponents();

        setVisible(true);
    }

    private void createComponents() {
        SaleController controller = new SaleController(new SaleJDBCDAO());
        Set<Sale> sales = controller.listSale(SessionManager.getInstance().getCurrentAccount().getId());
        createSaleTable(sales);
        add(createButtonsPanel(), BorderLayout.SOUTH);
    }

    private void createSaleTable(Set<Sale> sales) {
        String[] columnNames = {"Sale ID", "Date", "Name", "Price", "Quantity"};

        // Create a DefaultTableModel with column names
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

        // Populate the table model with sales data
        for (Sale sale : sales) {
            Object[] rowData = {sale.getSaleId(),
                    formatter.format(sale.getSaleDate()),
                    sale.getProductName(),
                    sale.getPrice(),
                    sale.getQuantity()};
            model.addRow(rowData);
        }

        // Create JTable with the model
        saleTable = new JTable(model);

        // Set table font and background color
        saleTable.setFont(tableFont);
        saleTable.setBackground(Color.WHITE);
        saleTable.setRowHeight(30); // Increase row height

        // Set table header font and background color
        JTableHeader header = saleTable.getTableHeader();
        header.setFont(headerFont);
        header.setBackground(backgroundColor);
        header.setForeground(Color.BLACK);

        saleTable.setRowSelectionAllowed(false);
        saleTable.setColumnSelectionAllowed(false);

        // Add the table to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(saleTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    switch (SessionManager.getInstance().getCurrentAccount().getRole()) {
                        case GUEST:
                            HotelManagementSystem.openGuestHomePage();
                            break;
                        case CLERK:
                            HotelManagementSystem.openClerkHomePage();
                            break;
                        case ADMIN:
                            HotelManagementSystem.openAdminHomePage();
                            break;
                        default:
                            throw new RuntimeException();
                    }
                } catch (RuntimeException exc) {
                    JOptionPane.showMessageDialog(null, "Invalid user info! Please contact admin.");
                }
            }
        });


        // Set button font, size, and background color
        backButton.setFont(headerFont);
        backButton.setForeground(Color.BLACK);

        return backButton;
    }
    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 row, 2 columns, horizontal gap 10, vertical gap 0
        buttonsPanel.setBackground(backgroundColor);

        JButton backButton = createBackButton();
        JButton payBillButton = createPayBillButton();

        buttonsPanel.add(backButton);
        buttonsPanel.add(payBillButton);

        return buttonsPanel;
    }

    private JButton createPayBillButton() {
        JButton payBillButton = new JButton("Pay Bill");
        payBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add payment logic here
                JOptionPane.showMessageDialog(null, "Payment completed successfully!");
            }
        });

        // Set button font, size, and background color
        payBillButton.setFont(headerFont);
        payBillButton.setForeground(Color.BLACK);


        return payBillButton;
    }

}
