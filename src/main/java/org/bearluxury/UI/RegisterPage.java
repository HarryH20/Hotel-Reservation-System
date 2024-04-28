package org.bearluxury.UI;

import org.bearluxury.account.*;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.bearluxury.controllers.AccountController;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class RegisterPage extends JFrame implements ActionListener {

    ImageIcon logo;

    private JPanel registerPanel;
    private JPanel cardPanel;
    private JScrollPane scrollPane;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailTextField;
    private JFormattedTextField phoneTextField;
    private JPasswordField passwordTextField;
    private JPasswordField confirmPasswordField;
    private JFormattedTextField cardNumberField;
    private JFormattedTextField dateField;
    private JFormattedTextField cvvField;
    private JButton registerButton;
    private JButton cmdLogin;

    private JLabel emptyFirstNameLabel;
    private JLabel emptyLastNameLabel;
    private JLabel emptyEmailLabel;
    private JLabel badEmailLabel;
    private JLabel emptyPhoneLabel;
    private JLabel emptyPasswordLabel;
    private JLabel badPasswordLabel;
    private JLabel emptyConfirmPasswordLabel;
    private JLabel emptyCardInfoLabel;

    private JLabel emailInUseLabel;
    private JLabel phoneInUseLabel;
    private JLabel passwordNotMatchLabel;

    private PasswordSpecifier passwordSpecifier = new PasswordSpecifier();

    public RegisterPage() {
        // Set window preferences
        setTitle("Register");
        setSize(1280, 920);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        getContentPane().setBackground(Style.backgroundColor);

        logo = new ImageIcon("src/main/resources/bbl-logo-transparent.png");
        JLabel logoLabel = new JLabel(logo);

        firstNameField = new JTextField();
        firstNameField.addActionListener(this);
        lastNameField = new JTextField();
        lastNameField.addActionListener(this);
        emailTextField = new JTextField();
        emailTextField.addActionListener(this);
        // Create formatted fields
        try {
            MaskFormatter phoneFormatter = new MaskFormatter("###-###-####");
            phoneTextField = new JFormattedTextField(phoneFormatter);
            phoneTextField.addActionListener(this);
            MaskFormatter dateFormatter = new MaskFormatter("##/##");
            dateField = new JFormattedTextField(dateFormatter);
            dateField.addActionListener(this);
            MaskFormatter cardFormatter = new MaskFormatter("####-####-####-####");
            cardNumberField = new JFormattedTextField(cardFormatter);
            cardNumberField.addActionListener(this);
            MaskFormatter cvvFormatter = new MaskFormatter("###");
            cvvField = new JFormattedTextField(cvvFormatter);
            cvvField.addActionListener(this);
        }catch(ParseException ignored){
        }
        passwordTextField = new JPasswordField();
        passwordTextField.addActionListener(this);
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.addActionListener(this);

        registerButton = new JButton("Register");
        registerButton.putClientProperty(FlatClientProperties.STYLE,
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        registerButton.addActionListener(this);

        registerPanel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 30 45", "fill,250:280"));
        registerPanel.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                "background:darken(@background,3%);");

        passwordTextField.putClientProperty(FlatClientProperties.STYLE,
                "showRevealButton:true");
        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE,
                "showRevealButton:true");

        JLabel header = new JLabel("Welcome to Baylor Bear Luxury");
        header.putClientProperty(FlatClientProperties.STYLE, "font:bold +7");

        JLabel description = new JLabel("Please fill in the information below to get started");
        description.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        emptyFirstNameLabel = new JLabel("First name is required");
        emptyFirstNameLabel.setForeground(Color.red);
        emptyLastNameLabel = new JLabel("Last name is required");
        emptyLastNameLabel.setForeground(Color.red);
        emptyEmailLabel = new JLabel("Email address is required");
        emptyEmailLabel.setForeground(Color.red);
        badEmailLabel = new JLabel("Email address not valid.");
        badEmailLabel.setForeground(Color.red);
        emptyPhoneLabel = new JLabel("Phone number is required");
        emptyPhoneLabel.setForeground(Color.red);
        emptyPasswordLabel = new JLabel("Field cannot be empty");
        emptyPasswordLabel.setForeground(Color.red);
        badPasswordLabel = new JLabel();
        badPasswordLabel.setForeground(Color.red);
        emptyConfirmPasswordLabel = new JLabel("Confirm password is required");
        emptyConfirmPasswordLabel.setForeground(Color.red);
        emptyCardInfoLabel = new JLabel("Card information is required");
        emptyCardInfoLabel.setForeground(Color.red);

        emailInUseLabel = new JLabel("This email is already in use");
        emailInUseLabel.setForeground(Color.red);
        phoneInUseLabel = new JLabel("This phone number is already in use");
        phoneInUseLabel.setForeground(Color.red);
        passwordNotMatchLabel = new JLabel("Passwords do not match");
        passwordNotMatchLabel.setForeground(Color.red);

        cardPanel = new JPanel(new MigLayout("fillx,insets 0"));
        cardPanel.putClientProperty(FlatClientProperties.STYLE, "background:darken(@background,3%);");
        cardPanel.setPreferredSize(new Dimension(0, 50));
        cardPanel.add(new JLabel("Date"));
        cardPanel.add(new JLabel("CVV"), "wrap");
        cardPanel.add(dateField);
        cardPanel.add(cvvField);

        registerPanel.add(logoLabel);
        registerPanel.add(header, "gapy 0");
        registerPanel.add(description);
        registerPanel.add(new JLabel("First name"), "gapy 0");
        registerPanel.add(firstNameField);
        registerPanel.add(new JLabel("Last name"), "gapy 0");
        registerPanel.add(lastNameField);
        registerPanel.add(new JLabel("Email"), "gapy 0");
        registerPanel.add(emailTextField);
        registerPanel.add(new JLabel("Phone"), "gapy 0");
        registerPanel.add(phoneTextField);
        registerPanel.add(new JLabel("Password"), "gapy 0");
        registerPanel.add(passwordTextField);
        registerPanel.add(new JLabel("Confirm password"), "gapy 0");
        registerPanel.add(confirmPasswordField, "gapy 0");
        registerPanel.add(new JLabel("Card number"));
        registerPanel.add(cardNumberField);
        registerPanel.add(cardPanel);
        registerPanel.add(registerButton, "gapy 0");
        registerPanel.add(createLoginLabel(), "gapy 0");

        scrollPane = new JScrollPane(registerPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.putClientProperty(FlatClientProperties.STYLE,
                "arc:20;" +
                "background:darken(@background,3%);");

        add(scrollPane);
    }

    private Component createLoginLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE,
                "background:null");
        cmdLogin = new JButton("<html><a href=\"#\">Log in now</a></html>");
        cmdLogin.putClientProperty(FlatClientProperties.STYLE,
                "border:3,3,3,3");
        cmdLogin.setContentAreaFilled(false);
        cmdLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdLogin.addActionListener(this);
        JLabel label = new JLabel("Already have an account?");
        label.putClientProperty(FlatClientProperties.STYLE,
                "[light]foreground:lighten(@foreground,30%);");
        panel.add(label);
        panel.add(cmdLogin);
        return panel;
    }

    private boolean checkCredentials() {
        boolean validCredentials = true;
        AccountController controller = new AccountController(new AccountJDBCDAO());

        // Check if fields are empty
        int addedComponentCount = 0;
        if (firstNameField.getText().isEmpty()) {
            registerPanel.add(emptyFirstNameLabel, 5 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else { registerPanel.remove(emptyFirstNameLabel); }
        if (lastNameField.getText().isEmpty()) {
            registerPanel.add(emptyLastNameLabel, 7 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else { registerPanel.remove(emptyLastNameLabel); }
        if (emailTextField.getText().isEmpty()) {
            registerPanel.add(emptyEmailLabel, 9 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else {
            registerPanel.remove(emptyEmailLabel);
            registerPanel.remove(emailInUseLabel);
            registerPanel.remove(badEmailLabel);

            // Check if email is in use
            for (Account account : controller.listAccounts()) {
                if (account.getEmail().equalsIgnoreCase(emailTextField.getText())) {
                    registerPanel.add(emailInUseLabel, 9 + addedComponentCount);
                    validCredentials = false;

                }
            }
            if(!EmailSpecifier.isValidEmail(emailTextField.getText())){
                registerPanel.add(badEmailLabel, 9 + addedComponentCount);
                validCredentials = false;
                addedComponentCount++;
            }
        }
        if (phoneTextField.getValue() == null) {
            registerPanel.add(emptyPhoneLabel, 11 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else {
            registerPanel.remove(emptyPhoneLabel);
            registerPanel.remove(phoneInUseLabel);

            // Check if phone is in use
            for (Account account : controller.listAccounts()) {
                if (account.getPhoneNumber() == Long
                        .parseLong(String.valueOf(phoneTextField.getValue())
                                .replaceAll("-",""))) {
                    registerPanel.add(phoneInUseLabel, 11 + addedComponentCount);
                    addedComponentCount++;
                    validCredentials = false;
                }
            }
        } // password is empty, show error
        if (passwordTextField.getText().isEmpty()) {
            registerPanel.add(emptyPasswordLabel, 13 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        }else{
            registerPanel.remove(emptyPasswordLabel);

            // password does not meet specification, show error
            if(!passwordSpecifier.checkPassword(passwordTextField.getText())){
                // if there is a problem with the password, it's not empty
                registerPanel.remove(emptyPasswordLabel);

                badPasswordLabel.setText(passwordSpecifier.getPasswordProblem());
                registerPanel.add(badPasswordLabel, 13 + addedComponentCount);
                addedComponentCount++;
                validCredentials = false;
            }else{
                registerPanel.remove(badPasswordLabel);
            }
        }
        if (confirmPasswordField.getText().isEmpty()) {
            registerPanel.add(emptyConfirmPasswordLabel, 15 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else {
            registerPanel.remove(emptyConfirmPasswordLabel);
            registerPanel.remove(passwordNotMatchLabel);

            // Check if passwords match
            if (!passwordTextField.getText().isEmpty()) {
                if (!passwordTextField.getText().equals(confirmPasswordField.getText())) {
                    registerPanel.add(passwordNotMatchLabel, 15 + addedComponentCount);
                    validCredentials = false;
                }
            }
        }
        // Check if card info is filled
        if (cardNumberField.getValue() == null || dateField.getValue() == null || cvvField.getValue() == null) {
            registerPanel.add(emptyCardInfoLabel, 18 + addedComponentCount);
            validCredentials = false;
        } else {
            registerPanel.remove(emptyCardInfoLabel);
        }

        setVisible(true);

        return validCredentials;
    }

    /////TEMP FIX: MADE ACCOUNT BUILDER ROLE GUEST
    private void registerAccount() {
        AccountController controller = new AccountController(new AccountJDBCDAO());
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailTextField.getText();
        // username is not needed. Using email for now
        String userName = emailTextField.getText();
        // remove unwanted "-" character from phone number
        long phoneNumber = Long.parseLong(phoneTextField.getText().replaceAll("-",""));
        String password = passwordTextField.getText();

        // BILLING INFO
        String cardNumber = cardNumberField.getText(); // contains "-" remove if needed
        String date = dateField.getText(); // contains "/"
        String cvv = dateField.getText();
        System.out.println(cardNumber);
        System.out.println(date);
        System.out.println(cvv);

        //FIXME
        Role role = Role.GUEST;
        controller.insertAccount(new Account(firstName,lastName, userName, email,phoneNumber,password, role));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmdLogin) {
            dispose();
            HotelManagementSystem.openLoginPage();
        } else if (e.getSource() == registerButton || e.getSource() == cvvField) {
            if (checkCredentials()) {
                registerAccount();
                JOptionPane.showMessageDialog(this, "Account successfully registered.");
                dispose();
                HotelManagementSystem.openLoginPage();
            }
        }
        // Move to next field when enter is pressed
        if (e.getSource() == firstNameField) {
            lastNameField.requestFocusInWindow();
        } else if (e.getSource() == lastNameField) {
            emailTextField.requestFocusInWindow();
        } else if (e.getSource() == emailTextField) {
            phoneTextField.requestFocusInWindow();
        } else if (e.getSource() == phoneTextField) {
            passwordTextField.requestFocusInWindow();
        } else if (e.getSource() == passwordTextField) {
            confirmPasswordField.requestFocusInWindow();
        } else if (e.getSource() == confirmPasswordField) {
            cardNumberField.requestFocusInWindow();
        } else if (e.getSource() == cardNumberField) {
            dateField.requestFocusInWindow();
        } else if (e.getSource() == dateField) {
            cvvField.requestFocusInWindow();
        }
    }
}