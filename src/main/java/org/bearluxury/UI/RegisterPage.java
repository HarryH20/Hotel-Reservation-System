package org.bearluxury.UI;

import org.bearluxury.account.AccountBuilder;
import org.bearluxury.account.Account;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import org.bearluxury.account.Role;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegisterPage extends JFrame implements ActionListener {

    Color backgroundColor = new Color(232,223,185,255);

    AccountBuilder accountBuilder;

    ImageIcon logo;

    private JPanel registerPanel;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailTextField;
    private JTextField phoneTextField;
    private JPasswordField passwordTextField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton cmdRegister;

    private JLabel emptyFirstNameLabel;
    private JLabel emptyLastNameLabel;
    private JLabel emptyEmailLabel;
    private JLabel emptyPhoneLabel;
    private JLabel emptyPasswordLabel;
    private JLabel emptyConfirmPasswordLabel;

    private JLabel emailInUseLabel;
    private JLabel phoneInUseLabel;
    private JLabel passwordNotMatchLabel;

    public RegisterPage() {
        setTitle("Register");
        setSize(1280, 920);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        getContentPane().setBackground(backgroundColor);

        accountBuilder = new AccountBuilder("src/main/resources/AccountList.csv");

        logo = new ImageIcon("src/main/resources/bbl-logo-transparent.png");
        JLabel logoLabel = new JLabel(logo);

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailTextField = new JTextField();
        phoneTextField = new JTextField();
        passwordTextField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        registerButton = new JButton("Register");
        registerButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        registerButton.addActionListener(this);

        registerPanel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 30 45", "fill,250:280"));
        registerPanel.setBackground(backgroundColor);
        registerPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:darken(@background,3%);");

        passwordTextField.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        confirmPasswordField.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");

        JLabel header = new JLabel("Welcome to Baylor Bear Luxury");
        header.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +7");

        JLabel description = new JLabel("Please fill in the information below to get started");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        emptyFirstNameLabel = new JLabel("First name is required");
        emptyFirstNameLabel.setForeground(Color.red);
        emptyLastNameLabel = new JLabel("Last name is required");
        emptyLastNameLabel.setForeground(Color.red);
        emptyEmailLabel = new JLabel("Email address is required");
        emptyEmailLabel.setForeground(Color.red);
        emptyPhoneLabel = new JLabel("Phone number is required");
        emptyPhoneLabel.setForeground(Color.red);
        emptyPasswordLabel = new JLabel("Password is required");
        emptyPasswordLabel.setForeground(Color.red);
        emptyConfirmPasswordLabel = new JLabel("Confirm password is required");
        emptyConfirmPasswordLabel.setForeground(Color.red);

        emailInUseLabel = new JLabel("This email is already in use");
        emailInUseLabel.setForeground(Color.red);
        phoneInUseLabel = new JLabel("This phone number is already in use");
        phoneInUseLabel.setForeground(Color.red);
        passwordNotMatchLabel = new JLabel("Passwords do not match");
        passwordNotMatchLabel.setForeground(Color.red);

        registerPanel.add(logoLabel);
        registerPanel.add(header, "gapy 10");
        registerPanel.add(description);
        registerPanel.add(new JLabel("First name"), "gapy 6");
        registerPanel.add(firstNameField);
        registerPanel.add(new JLabel("Last name"), "gapy 6");
        registerPanel.add(lastNameField);
        registerPanel.add(new JLabel("Email"), "gapy 6");
        registerPanel.add(emailTextField);
        registerPanel.add(new JLabel("Phone"), "gapy 6");
        registerPanel.add(phoneTextField);
        registerPanel.add(new JLabel("Password"), "gapy 6");
        registerPanel.add(passwordTextField);
        registerPanel.add(new JLabel("Confirm password"), "gapy 6");
        registerPanel.add(confirmPasswordField);
        registerPanel.add(registerButton, "gapy 10");
        registerPanel.add(createRegisterLabel(), "gapy 10");

        add(registerPanel);
    }

    private Component createRegisterLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        cmdRegister = new JButton("<html><a href=\"#\">Log in now</a></html>");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(this);
        JLabel label = new JLabel("Already have an account?");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);");
        panel.add(label);
        panel.add(cmdRegister);
        return panel;
    }

    private Boolean checkCredentials() {
        Boolean validCredentials = true;
        AccountBuilder accountBuilder = new AccountBuilder("src/main/resources/AccountList.csv");
        ArrayList<Account> accounts = accountBuilder.getAccountList();

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

            // Check if email is in use
            for (Account account : accounts) {
                if (account.getEmail().equalsIgnoreCase(emailTextField.getText())) {
                    registerPanel.add(emailInUseLabel, 9 + addedComponentCount);
                    validCredentials = false;
                }
            }
        }
        if (phoneTextField.getText().isEmpty()) {
            registerPanel.add(emptyPhoneLabel, 11 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else {
            registerPanel.remove(emptyPhoneLabel);
            registerPanel.remove(phoneInUseLabel);

            // Check if phone is in use
            for (Account account : accounts) {
                if (account.getPhoneNumber() == Long.parseLong(phoneTextField.getText())) {
                    registerPanel.add(phoneInUseLabel, 11 + addedComponentCount);
                    validCredentials = false;
                }
            }
        }
        if (passwordTextField.getText().isEmpty()) {
            registerPanel.add(emptyPasswordLabel, 13 + addedComponentCount);
            addedComponentCount++;
            validCredentials = false;
        } else { registerPanel.remove(emptyPasswordLabel); }
        if (confirmPasswordField.getText().isEmpty()) {
            registerPanel.add(emptyConfirmPasswordLabel, 15 + addedComponentCount);
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

        setVisible(true);

        return validCredentials;
    }

    /////TEMP FIX: MADE ACCOUNT BUILDER ROLE GUEST
    private void registerAccount() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailTextField.getText();
        // username is not needed. Using email for now
        String userName = emailTextField.getText();
        long phoneNumber = Long.parseLong(phoneTextField.getText());
        String password = passwordTextField.getText();
        //FIXME
        Role role = Role.GUEST;
        accountBuilder.addAccount(firstName, lastName, email, userName, phoneNumber, password, role);
        accountBuilder.writeAccount("AccountList.csv");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmdRegister) {
            dispose();
            HotelManagementSystem.openLoginPage();
        } else if (e.getSource() == registerButton) {
            if (checkCredentials()) {
                registerAccount();
                JOptionPane.showMessageDialog(this, "Account successfully registered.");
                dispose();
                HotelManagementSystem.openLoginPage();
            }
        }
    }
}