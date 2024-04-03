package org.bearluxury;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterAccountPane extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton;

    public RegisterAccountPane () {
        setTitle("Register Account");
        setSize(300, 150);
<<<<<<< HEAD
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
=======
>>>>>>> master
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));

        JLabel usernameLabel = new JLabel("Username:");
        panel.add(usernameLabel);

        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        panel.add(passwordField);

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                //need to add functionality for csv
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
            }
        });
        panel.add(registerButton);

        getContentPane().add(panel);
        setVisible(true);
    }

    public static void openAccountPane() {
        FlatLightLaf.setup();
        RegisterAccountPane window = new RegisterAccountPane();
        window.setVisible(true);
    }
}
