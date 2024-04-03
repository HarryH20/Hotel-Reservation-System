package org.bearluxury;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HotelHomePage extends JFrame {
    public HotelHomePage() {
        setTitle("Baylor Bear Luxury");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 720);
        setLocationRelativeTo(null); // Center the frame on the screen


        Color backgroundColor = new Color(232, 223, 185, 255);
        getContentPane().setBackground(backgroundColor);


        JPanel logoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    BufferedImage hotelImage = ImageIO.read(new File("hotelStockImage.jpg"));
                    BufferedImage logoImage = ImageIO.read(new File("logo.png"));


                    int hotelX = (getWidth() - hotelImage.getWidth()) / 2;
                    int hotelY = (getHeight() - hotelImage.getHeight()) / 2;
                    g.drawImage(hotelImage, hotelX, hotelY, this);


                    int logoX = (getWidth() - logoImage.getWidth()) / 2;
                    int logoY = (getHeight() - logoImage.getHeight()) / 6;
                    g.drawImage(logoImage, logoX, logoY, this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        logoPanel.setBackground(backgroundColor);


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
        seeReservations.setFont(font);
        seeReservations.setForeground(Color.BLACK);
        seeReservations.addActionListener(new openViewReservationPane());
        reservePanel.add(seeReservations);

        JButton addUser = new JButton("Register");
        JButton addRoom = new JButton("Add Room");

        seeReservations.setFont(font);
        seeReservations.setForeground(Color.BLACK);
        addUser.setFont(font);
        addRoom.addActionListener(new openAddRoomPane());
        addUser.addActionListener(new openRegistration());
        addUser.setForeground(Color.BLACK);
        addRoom.setFont(font);
        addRoom.setForeground(Color.BLACK);

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

    private class openViewReservationPane implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            ReservationCatalog reservations = new ReservationCatalog();
            reservations.setReservations(new ReservationBuilder("ReservationList.csv").getReservationList());
            BookedReservationsGUI catalogPane = new BookedReservationsGUI(reservations);
            catalogPane.setVisible(true);
        }
    }
    private class openAddRoomPane implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            AddRoomPane.openAddRoomPane();
        }
    }
    private class openRegistration implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterPane pane = new RegisterPane();
            pane.setVisible(true);
        }
    }

}
