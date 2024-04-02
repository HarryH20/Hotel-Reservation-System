package org.bearluxury;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame implements ActionListener {

    Color backgroundColor = new Color(232,223,185,255);

    ImageIcon logo;

    private JPanel loginPanel;

    private JTextField emailTextField;
    private JPasswordField passwordTextField;
    private JButton loginButton;

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
        JButton cmdRegister = new JButton("<html><a href=\"#\">Register now</a></html>");
        cmdRegister.putClientProperty(FlatClientProperties.STYLE, "" +
                "border:3,3,3,3");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JLabel label = new JLabel("Don't have an account?");
        label.putClientProperty(FlatClientProperties.STYLE, "" +
                "[light]foreground:lighten(@foreground,30%);");
        panel.add(label);
        panel.add(cmdRegister);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            loginPanel.add(wrongMsg, "gapy 8", 7);
            this.setVisible(true);
        }
    }

    public static void main(String[] args) {
        FlatRobotoFont.install();
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatLightLaf.setup();
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}