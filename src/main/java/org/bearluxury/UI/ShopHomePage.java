package org.bearluxury.UI;

import com.formdev.flatlaf.FlatClientProperties;
import jdk.jfr.Category;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class ShopHomePage extends JFrame {

    JPanel sideBar;
    JPanel itemPanel;

    JButton backButton;

    JLabel categoryLabel;
    JList<String> categories;
    JScrollPane categoryPane;

    JLabel sortLabel;
    JComboBox sortDropDown;


    public ShopHomePage() {
        setTitle("Shop Home");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Style.backgroundColor);

        sideBar = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 10));
        sideBar.setPreferredSize(new Dimension(250, this.getHeight()));

        itemPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 20, 20));
        itemPanel.setBackground(Style.backgroundColor);

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

        ItemCard itemCard = new ItemCard("Apple", 2.50);

        itemPanel.add(itemCard);

        add(sideBar, BorderLayout.WEST);
        add(itemPanel, BorderLayout.CENTER);
    }

    private JLabel fillSpace(int whitespace) {
        StringBuilder filler = new StringBuilder();
        for (int i = 0; i < whitespace; ++i) {
            filler.append(" ");
        }
        return new JLabel(String.valueOf(filler));
    }
}
