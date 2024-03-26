package org.bearluxury;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelHomePage extends JFrame {
    public HotelHomePage() {
        setTitle("Baylor Bear Luxury");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 720);
        setLocationRelativeTo(null); // Center the frame on the screen


        Color backgroundColor = new Color(232, 223, 185, 255);
        getContentPane().setBackground(backgroundColor);


        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(backgroundColor);


        ImageIcon logoIcon = new ImageIcon("bbl-logo.png");
        JLabel logoLabel = new JLabel(logoIcon);

        int logoWidth = logoIcon.getIconWidth() * 2; // Doubling the width
        int logoHeight = logoIcon.getIconHeight() * 2; // Doubling the height
        logoLabel.setPreferredSize(new Dimension(logoWidth, logoHeight));
        logoPanel.add(logoLabel);


        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(backgroundColor);
        JLabel welcomeLabel = new JLabel("Welcome to Baylor Bear Luxury!");
        Font font = new Font("Goudy Old Style", Font.PLAIN, 30);
        welcomeLabel.setFont(font);
        welcomePanel.add(welcomeLabel);


        JPanel reservePanel = new JPanel();
        reservePanel.setBackground(backgroundColor);
        JButton reserveButton = new JButton("Get A Room");
        reserveButton.setFont(font);
        reserveButton.setForeground(Color.BLACK);
        reserveButton.addActionListener(new openHotelManagmentPane());
        reservePanel.add(reserveButton);

        JButton seeReservations = new JButton("See All Reservations");
        JButton addUser = new JButton("Register");
        JButton addRoom = new JButton("Add Room");

        seeReservations.setFont(font);
        seeReservations.setForeground(Color.BLACK);
        addUser.setFont(font);
        addUser.setForeground(Color.BLACK);
        addRoom.setFont(font);
        addRoom.setForeground(Color.BLACK);

        reservePanel.add(seeReservations);
        reservePanel.add(addUser);
        reservePanel.add(addRoom);



        setLayout(new BorderLayout());
        add(logoPanel, BorderLayout.CENTER);
        add(welcomePanel, BorderLayout.NORTH);
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
