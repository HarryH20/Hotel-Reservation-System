package org.bearluxury.UI;

import com.formdev.flatlaf.FlatClientProperties;
import org.bearluxury.product.PRODUCT_TYPE;
import org.bearluxury.product.Product;
import org.bearluxury.product.ProductCatalog;

import javax.swing.*;
import java.awt.*;

public class ShopHomePage extends JFrame {

    JPanel sideBar;
    JPanel itemPanel;
    JPanel checkoutPanel;

    JButton backButton;

    JLabel categoryLabel;
    JList<String> categories;
    JScrollPane categoryPane;

    JLabel sortLabel;
    JComboBox sortDropDown;

    JScrollPane productScrollPane;

    JLabel cartLabel;


    public ShopHomePage(ProductCatalog productCatalog) {
        setTitle("Shop Home");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Style.backgroundColor);

        sideBar = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 10));
        sideBar.setPreferredSize(new Dimension(230, this.getHeight()));

        itemPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20));
        itemPanel.setBackground(Style.backgroundColor);
        itemPanel.setPreferredSize(new Dimension(0, 150 * (productCatalog.getProducts().size() / 3)));

        checkoutPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 10));
        checkoutPanel.setPreferredSize(new Dimension(180, this.getHeight()));

        backButton = new JButton("Back");

        categoryLabel = new JLabel("Category");
        categoryLabel.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");

        String[] categoryTypes = {"All items", "Clothing", "Accessories", "Artesian Goods", "Toiletries", "Pharmaceutical"};
        categories = new JList<>(categoryTypes);
        categoryPane = new JScrollPane(categories);

        sortLabel = new JLabel("Sort By");
        sortLabel.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");
        String[] sortTypes = {"None", "Price: High to Low", "Price: Low to High"};
        sortDropDown = new JComboBox(sortTypes);

        sideBar.add(backButton);
        sideBar.add(fillSpace(100));
        sideBar.add(categoryLabel);
        sideBar.add(fillSpace(10));
        sideBar.add(categoryPane);
        sideBar.add(fillSpace(100));
        sideBar.add(sortLabel);
        sideBar.add(sortDropDown);

        for (Product product : productCatalog.getProducts()) {
            itemPanel.add(new ProductCard(product));
        }

        productScrollPane = new JScrollPane(itemPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(sideBar, BorderLayout.WEST);
        add(productScrollPane, BorderLayout.CENTER);
        add(checkoutPanel, BorderLayout.EAST);
    }

    private JLabel fillSpace(int whitespace) {
        StringBuilder filler = new StringBuilder();
        for (int i = 0; i < whitespace; ++i) {
            filler.append(" ");
        }
        return new JLabel(String.valueOf(filler));
    }
}
