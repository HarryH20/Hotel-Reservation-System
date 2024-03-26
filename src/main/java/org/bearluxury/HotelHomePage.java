package org.bearluxury;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelHomePage extends JFrame {
    public HotelHomePage() {
        setTitle("Baylor Bear Luxury");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Background color
        Color backgroundColor = new Color(232, 223, 185, 255);
        getContentPane().setBackground(backgroundColor);

        // Logo panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(backgroundColor);

        // Load and add the logo
        ImageIcon logoIcon = new ImageIcon("bbl-logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        // Increase the size of the logo
        int logoWidth = logoIcon.getIconWidth() * 2; // Doubling the width
        int logoHeight = logoIcon.getIconHeight() * 2; // Doubling the height
        logoLabel.setPreferredSize(new Dimension(logoWidth, logoHeight));
        logoPanel.add(logoLabel);

        // Welcome message panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(backgroundColor);
        JLabel welcomeLabel = new JLabel("Welcome to Baylor Bear Luxury!");
        Font font = new Font("Goudy Old Style", Font.PLAIN, 30);
        welcomeLabel.setFont(font);
        welcomePanel.add(welcomeLabel);

        // Reserve button panel
        JPanel reservePanel = new JPanel();
        reservePanel.setBackground(backgroundColor);
        JButton reserveButton = new JButton("Get A Room");
        reserveButton.setFont(font);
        reserveButton.setForeground(Color.BLACK);
        reserveButton.addActionListener(new openHotelManagmentPane());
        reservePanel.add(reserveButton);

        // Layout setup
        setLayout(new BorderLayout());
        add(logoPanel, BorderLayout.CENTER); // Place logo in the center
        add(welcomePanel, BorderLayout.NORTH); // Move the welcome message to the top
        add(reservePanel, BorderLayout.SOUTH);
    }
    private class openHotelManagmentPane implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HotelManagementSystem.openHotelManagmentSystem();
        }
    }

}
