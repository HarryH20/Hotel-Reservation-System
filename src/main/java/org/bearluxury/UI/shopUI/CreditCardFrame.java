package org.bearluxury.UI.shopUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import net.miginfocom.swing.MigLayout;
import org.bearluxury.UI.HotelManagementSystem;

public class CreditCardFrame extends JFrame {


    private JTextField cardNumberField;
    private JTextField expDateField;
    private JTextField cvvField;
    private JTextField cardHolderNameField;
    public CreditCardFrame() {
        setTitle("Credit Card Information");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel creditCardPanel = new JPanel(new MigLayout("wrap 2", "[][grow]"));

        cardNumberField = new JTextField(20);
        expDateField = new JTextField(20);
        cvvField = new JTextField(20);
        cardHolderNameField = new JTextField(20);

        creditCardPanel.add(new JLabel("Card Number"));
        creditCardPanel.add(cardNumberField, "wrap");
        creditCardPanel.add(new JLabel("Expiration Date (MM/YY)"));
        creditCardPanel.add(expDateField, "wrap");
        creditCardPanel.add(new JLabel("CVV"));
        creditCardPanel.add(cvvField, "wrap");
        creditCardPanel.add(new JLabel("Cardholder Name"));
        creditCardPanel.add(cardHolderNameField, "wrap");

        JButton submitButton = new JButton("Finish Registration"); // Create the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cvv = cvvField.getText();
                if (isValidCVV(cvv)) {
                    JOptionPane.showMessageDialog(null, "Account successfully registered.");
                    HotelManagementSystem.openLoginPage();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid CVV. CVV must be between 3 and 4 digits and contain only numbers.");
                }
            }
        });

        // Add the submit button to the panel
        creditCardPanel.add(submitButton, "span 2, align center");

        add(creditCardPanel);
    }

    private boolean isValidCVV(String cvv) {
        // Check if the input is numeric and has length between 3 and 4
        return Pattern.matches("\\d{3,4}", cvv);
    }

    public String getCardNumberField() {
        return cardNumberField.getText();
    }

    public String getExpDateField() {
        return expDateField.getText();
    }

    public String getCvvField() {
        return cvvField.getText();
    }

    public String getCardHolderNameField() {
        return cardHolderNameField.getText();
    }
}

