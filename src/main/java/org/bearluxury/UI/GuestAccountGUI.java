package org.bearluxury.UI;

import org.bearluxury.Billing.SaleJDBCDAO;
import org.bearluxury.account.Account;
import org.bearluxury.account.Guest;
import org.bearluxury.account.Role;
import org.bearluxury.controllers.SaleController;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;
import java.util.stream.Collectors;

public class GuestAccountGUI extends JFrame {
    private final Color backgroundColor = new Color(232, 223, 185);
    private final Color tableHeaderColor = new Color(184, 134, 11);
    private final Font tableHeaderFont = new Font("Arial", Font.BOLD, 18);
    private final Font tableFont = new Font("Arial", Font.BOLD, 14);

    public GuestAccountGUI(Set<Guest> accounts) {
        setTitle("Account Information");
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        DefaultTableModel model = createTableModel();

        JTable table = createTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panel = createPanel(scrollPane);

        getContentPane().setBackground(backgroundColor);

        fillTableRows(accounts, model);

        JButton backButton = createBackButton();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(backgroundColor);
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.add(backButton, BorderLayout.WEST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Account ID","First Name", "Last Name", "Email", "Phone Number", "Role"};
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

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                SaleController controller = new SaleController(new SaleJDBCDAO());
                if (evt.getClickCount() == 2) {
                    Point pnt = evt.getPoint();
                    int row = table.rowAtPoint(pnt);
                    BillingPage billingPage = new BillingPage(Integer.parseInt(table.getValueAt(row,0).toString()));
                    billingPage.updatePage(controller.listSale(Integer.parseInt(table.getValueAt(row,0).toString())));
                    billingPage.setVisible(true);
                }
            }
        });

        return table;
    }

    private JPanel createPanel(JScrollPane scrollPane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(backgroundColor);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void fillTableRows(Set<Guest> accounts, DefaultTableModel model) {
        // Fill the table rows with account information
        accounts.stream().filter(account -> account.getRole() == Role.GUEST).
                collect(Collectors.toSet()).
                forEach(account -> model.addRow(new Object[]{
                        String.valueOf(account.getId()),
                        account.getFirstName(),
                        account.getLastName(),
                        account.getEmail(),
                        account.getPhoneNumber(),
                        account.getRole().toString()
                }));
    }
}

