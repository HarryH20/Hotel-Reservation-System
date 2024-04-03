package org.bearluxury;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginPage extends JFrame implements ActionListener {

    Color backgroundColor = new Color(232,223,185,255);

    ImageIcon logo;

    private JPanel loginPanel;

    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private JButton cmdRegister;

    private JLabel wrongMsg;

    public LoginPage() {
        setTitle("Login");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new MigLayout("fill,insets 20", "[center]", "[center]"));
        getContentPane().setBackground(backgroundColor);

        logo = new ImageIcon("bbl-logo-transparent.png");
        JLabel logoLabel = new JLabel(logo);

        emailTextField = new JTextField();
        passwordTextField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]background:darken(@background,10%);" +
                "[dark]background:lighten(@background,10%);" +
                "borderWidth:0;" +
                "focusWidth:0;" +
                "innerFocusWidth:0");
        loginButton.addActionListener(this);

        loginPanel = new JPanel(new MigLayout("wrap,fillx,insets 0 45 30 45", "fill,250:280"));
        loginPanel.setBackground(backgroundColor);
        loginPanel.putClientProperty(FlatClientProperties.STYLE, "" +
                "arc:20;" +
                "background:darken(@background,3%);");

        passwordTextField.putClientProperty(FlatClientProperties.STYLE, "" +
                "showRevealButton:true");
        JLabel header = new JLabel("Welcome back!");
        header.putClientProperty(FlatClientProperties.STYLE, "" + "font:bold +10");

        JLabel description = new JLabel("Please sign in to access your account");
        description.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);" +
                "[dark]foreground:darken(@foreground,30%)");

        wrongMsg = new JLabel("Wrong username or password");
        wrongMsg.setForeground(Color.red);

        loginPanel.add(logoLabel);
        loginPanel.add(header, "gapy 10");
        loginPanel.add(description);
        loginPanel.add(new JLabel("Email"), "gapy 8");
        loginPanel.add(emailTextField);
        loginPanel.add(new JLabel("Password"), "gapy 8");
        loginPanel.add(passwordTextField);
        loginPanel.add(loginButton, "gapy 10");
        loginPanel.add(createRegisterLabel(), "gapy 10");

        add(loginPanel);
    }

    private Component createRegisterLabel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.putClientProperty(FlatClientProperties.STYLE, "" +
                "background:null");
        cmdRegister = new JButton("<html><a href=\"#\">Register now</a></html>");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdRegister.addActionListener(this);
        JLabel label = new JLabel("Don't have an account?");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);");
        panel.add(label);
        panel.add(cmdRegister);
        return panel;
    }

    private Boolean doesAccountExist(String email, String password) {
        AccountBuilder accountBuilder = new AccountBuilder("AccountList.csv");
        ArrayList<Account> accounts = accountBuilder.getAccountList();
        for (Account account : accounts) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            if (doesAccountExist(emailTextField.getText(), passwordTextField.getText())) {
                dispose();
                HotelManagementSystem.openHomePage();
            } else {
                loginPanel.add(wrongMsg, "gapy 8", 7);
                this.setVisible(true);
            }
        } else if (e.getSource() == cmdRegister) {
            dispose();
            HotelManagementSystem.openRegisterPage();
        }
    }

}