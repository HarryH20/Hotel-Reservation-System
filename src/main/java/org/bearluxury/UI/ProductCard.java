package org.bearluxury.UI;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.bearluxury.product.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductCard extends JPanel implements ActionListener {

    Product product;
    ShopHomePage shopHomePage;

    JPanel northPanel;
    JPanel leftPanel;
    JPanel rightPanel;

    JLabel productName;
    JLabel productPrice;
    JLabel productStock;

    JTextField quantityTextField;
    JButton minusButton;
    JButton plusButton;

    int itemQuantity;

    public ProductCard(Product product, ShopHomePage shopHomePage) {
        putClientProperty(FlatClientProperties.STYLE, "arc:20;");
        setPreferredSize(new Dimension(250, 100));
        setLayout(new BorderLayout(10, 0));

        this.product = product;
        this.shopHomePage = shopHomePage;

        northPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
        leftPanel = new JPanel(new MigLayout("fill, insets 10"));
        rightPanel = new JPanel(new FlowLayout());

        productName = new JLabel(product.getName());
        productName.putClientProperty(FlatClientProperties.STYLE, "font:plain +8");
        productPrice = new JLabel("$" + String.format("%.2f", product.getPrice()));
        productPrice.putClientProperty(FlatClientProperties.STYLE, "font:bold +2");
        productStock = new JLabel("Stock: " + product.getQuantity());

        quantityTextField = new JTextField("0", 2);
        minusButton = new JButton("-");
        minusButton.setEnabled(false);
        minusButton.addActionListener(this);
        plusButton = new JButton("+");
        plusButton.addActionListener(this);

        northPanel.add(productName);

        leftPanel.add(productPrice, "wrap");
        leftPanel.add(productStock);

        rightPanel.add(minusButton);
        rightPanel.add(quantityTextField);
        rightPanel.add(plusButton);

        add(northPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);

        itemQuantity = 0;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plusButton) {
            itemQuantity++;
            quantityTextField.setText(String.valueOf(itemQuantity));
            shopHomePage.updateCart(product, itemQuantity);
            minusButton.setEnabled(true);
        } else if (e.getSource() == minusButton) {
            itemQuantity--;
            quantityTextField.setText(String.valueOf(itemQuantity));
            shopHomePage.updateCart(product, itemQuantity);
            if (itemQuantity == 0) {
                minusButton.setEnabled(false);
            }
        }
    }
}
