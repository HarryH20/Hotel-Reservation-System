package org.bearluxury.UI.shopUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

        JButton submitButton = new JButton("Finish Registration");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cvv = cvvField.getText();
                String expDate = expDateField.getText();
                String cardHolderName = cardHolderNameField.getText();

                boolean validCVV = isValidCVV(cvv);
                boolean validExpDate = isValidExpDate(expDate);
                boolean validName = isValidCardHolderName(cardHolderName);

                if (validCVV && validExpDate && validName) {
                    JOptionPane.showMessageDialog(null, "Account successfully registered.");
                    HotelManagementSystem.openLoginPage();
                    dispose();
                } else {
                    String errorMessage = "";
                    if (!validCVV) {
                        errorMessage += "Invalid CVV. CVV must be between 3 and 4 digits and contain only numbers.\n";
                    }
                    if (!validExpDate) {
                        errorMessage += "Invalid Expiration Date. Please enter in MM/YY format and should not be past today's date.\n";
                    }
                    if (!validName) {
                        errorMessage += "Invalid Cardholder Name. Name must contain only alphabetical characters.\n";
                    }
                    JOptionPane.showMessageDialog(null, errorMessage);
                }
            }
        });

        creditCardPanel.add(submitButton, "span 2, align center");

        add(creditCardPanel);
    }

    private boolean isValidCVV(String cvv) {
        return Pattern.matches("\\d{3,4}", cvv);
    }

    private boolean isValidExpDate(String expDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
        dateFormat.setLenient(false);
        try {
            Date expirationDate = dateFormat.parse(expDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(expirationDate);
            cal.set(Calendar.DAY_OF_MONTH, 1); // Set day to 1 to ignore day validation
            expirationDate = cal.getTime();
            Date today = new Date();
            return expirationDate.compareTo(today) >= 0;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isValidCardHolderName(String name) {
        return Pattern.matches("[a-zA-Z]+", name);
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
