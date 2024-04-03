package org.bearluxury;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends JFrame implements ActionListener {

    Color backgroundColor = new Color(232,223,185,255);

    ImageIcon logo;

    private JPanel registerPanel;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailTextField;
    private JTextField phoneTextField;
    private JPasswordField passwordTextField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    JButton cmdRegister;

    public RegisterPage() {
        setTitle("Register");
        setSize(1280, 820);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        getContentPane().setBackground(backgroundColor);

        logo = new ImageIcon("bbl-logo-transparent.png");
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

    public void openLoginPanel() {
        dispose();
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cmdRegister) {
            openLoginPanel();
        }
    }
}