package org.bearluxury.UI.shopUI;

import com.formdev.flatlaf.FlatClientProperties;
import org.bearluxury.Billing.SaleJDBCDAO;
import org.bearluxury.UI.HotelManagementSystem;
import org.bearluxury.controllers.ProductController;
import org.bearluxury.controllers.SaleController;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductJDBCDAO;
import org.bearluxury.shop.Sale;
import org.bearluxury.state.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckoutDialog extends JDialog implements ActionListener {
    private final double SALES_TAX = 0.0625;

    JFrame parent;

    Map<Product, Integer> cart;

    JPanel cartPanel;
    JPanel centerPanel;
    JPanel summaryPanel;
    JPanel leftPanel;
    JPanel rightPanel;
    JPanel totalPanel;
    JPanel purchasePanel;

    JLabel cartLabel;
    DefaultListModel<String> listModel;
    JList<String> cartList;
    JScrollPane cartScrollPane;

    JLabel summaryLabel;
    JLabel totalProductsLabel;
    JLabel totalProductsAmountLabel;
    JLabel taxLabel;
    JLabel taxAmountLabel;

    JLabel overallTotalLabel;
    JLabel overallTotalAmountLabel;
    double overallTotalCost;

    JButton purchaseButton;

    public CheckoutDialog(JFrame parent, Map<Product, Integer> cart, double totalPrice) {
        super(parent, "Checkout", true);
        setLocationRelativeTo(parent);
        setSize(340, 450);
        setResizable(false);

        this.parent = parent;
        this.cart = cart;

        // Cart panel
        cartPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        cartPanel.setPreferredSize(new Dimension(this.getWidth(), 300));

        cartLabel = new JLabel("Your Cart");
        cartLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        listModel = new DefaultListModel<>();
        cartList = new JList<>(listModel);
        cartList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        cartList.setFocusable(false);
        this.cart.forEach((product, quantity) -> listModel.addElement(quantity + "x " + product.getName() + " - $" + (product.getPrice() * quantity)));

        cartScrollPane = new JScrollPane(cartList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cartScrollPane.setPreferredSize(new Dimension(300, 200));

        summaryLabel = new JLabel("Purchase Summary");
        summaryLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        cartPanel.add(cartLabel);
        cartPanel.add(cartScrollPane);
        cartPanel.add(summaryLabel);

        centerPanel = new JPanel(new BorderLayout());

        // Summary panel
        summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        leftPanel = new JPanel(new GridLayout(2, 1));
        rightPanel = new JPanel(new GridLayout(2, 1));

        totalProductsLabel = new JLabel();
        // Get number of items
        AtomicInteger totalItems = new AtomicInteger();
        cart.forEach((product, quantity) -> totalItems.addAndGet(quantity));
        if (totalItems.get() == 1) {
            totalProductsLabel.setText("1 item");
        } else {
            totalProductsLabel.setText(totalItems + " items");
        }
        taxLabel = new JLabel("Sales tax");
        leftPanel.add(totalProductsLabel);
        leftPanel.add(taxLabel);

        totalProductsAmountLabel = new JLabel("$" + String.format("%.2f", totalPrice));
        totalProductsAmountLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        taxAmountLabel = new JLabel("$" + String.format("%.2f", totalPrice * SALES_TAX));
        taxAmountLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold");
        rightPanel.add(totalProductsAmountLabel);
        rightPanel.add(taxAmountLabel);

        summaryPanel.add(leftPanel, BorderLayout.WEST);
        summaryPanel.add(rightPanel, BorderLayout.EAST);

        totalPanel = new JPanel(new BorderLayout());
        overallTotalLabel = new JLabel(" Total:");
        overallTotalLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");
        overallTotalCost = totalPrice + (totalPrice * SALES_TAX);
        overallTotalAmountLabel = new JLabel("$" + String.format("%.2f", overallTotalCost) + " ");
        overallTotalAmountLabel.putClientProperty(FlatClientProperties.STYLE, "font:bold +8");
        totalPanel.add(overallTotalLabel, BorderLayout.WEST);
        totalPanel.add(overallTotalAmountLabel, BorderLayout.EAST);

        centerPanel.add(summaryPanel, BorderLayout.NORTH);
        centerPanel.add(totalPanel, BorderLayout.CENTER);


        // Checkout panel
        purchasePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        purchaseButton = new JButton("Confirm purchase");
        purchaseButton.setPreferredSize(new Dimension(200, 30));
        purchaseButton.addActionListener(this); // Add ActionListener to the purchaseButton
        purchasePanel.add(purchaseButton);

        add(cartPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(purchasePanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == purchaseButton) {
            // Update database quantities
            ProductController productController = null;
            try {
                productController = new ProductController(new ProductJDBCDAO());
                SaleController controller = new SaleController(new SaleJDBCDAO());
                for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
                    Product product = entry.getKey();
                    int quantity = entry.getValue();
                    // Decrease the quantity of the product in the database
                    productController.removeStock(product.getId(), quantity);
                    Sale sale = new Sale(new Date(),product.getName(),product.getPrice(),quantity);
                    sale.setAccountId(SessionManager.getInstance().getCurrentAccount().getId());
                    controller.insertSale(sale);

                }
                // Close the dialog
                dispose();
                parent.dispose();
                HotelManagementSystem.openShopHomePage();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
        }
    }
}

