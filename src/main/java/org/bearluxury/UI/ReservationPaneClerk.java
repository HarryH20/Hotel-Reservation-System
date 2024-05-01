package org.bearluxury.UI;

import com.github.lgooddatepicker.components.DatePicker;
import org.bearluxury.Billing.SaleJDBCDAO;
import org.bearluxury.account.GuestAccountJDBCDAO;
import org.bearluxury.account.Role;
import org.bearluxury.controllers.*;
import org.bearluxury.room.RoomJDBCDAO;
import org.bearluxury.shop.Sale;
import org.bearluxury.state.SessionManager;
import org.bearluxury.reservation.Reservation;
import org.bearluxury.reservation.ReservationJDBCDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.NoSuchElementException;

/*
 * This is the class for the reservation pane is not completed.
 * Attempting to make it work with a calendar need new maven dependacies.
 * It is in its own seperate class and called by openReservationPane().
 */
public class ReservationPaneClerk extends JFrame {
    private Container c;
    private JLabel title;
    private JTextField roomId;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JComboBox<String> roomType;
    private JSpinner guestNumber;
    private JButton submitButton;
    private DatePicker checkInDatePicker;
    private DatePicker checkOutDatePicker;

    public ReservationPaneClerk(int id, LocalDate checkIn, LocalDate checkOut) {
        setTitle("Reservation Form");
        setBounds(300, 90, 500, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Room Reservation");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(100, 30);
        c.add(title);

        JPanel reservationPanel = new JPanel();
        reservationPanel.setBorder(BorderFactory.createTitledBorder("Reservation Information"));
        reservationPanel.setBounds(40, 80, 400, 320);
        reservationPanel.setLayout(null);

        JLabel roomIdLabel = new JLabel("Room ID:");
        roomIdLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        roomIdLabel.setBounds(20, 30, 100, 20);
        reservationPanel.add(roomIdLabel);
        roomId = new JTextField();
        roomId.setText(String.valueOf(id));
        roomId.setEditable(false);
        GuestAccountController controller = new GuestAccountController(new GuestAccountJDBCDAO());

        roomId.setFont(new Font("Arial", Font.PLAIN, 15));
        roomId.setBounds(170, 30, 190, 20);
        reservationPanel.add(roomId);


        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        firstNameLabel.setBounds(20, 60, 100, 20);
        reservationPanel.add(firstNameLabel);
        firstName = new JTextField();
        firstName.setFont(new Font("Arial", Font.PLAIN, 15));
        firstName.setBounds(170, 60, 190, 20);
        if(SessionManager.getInstance().getCurrentAccount().getRole() ==Role.GUEST) {
            String firstNameInfo = controller.getAccount(SessionManager.getInstance().getCurrentAccount().getEmail()).
                    orElseThrow(() -> new NoSuchElementException("Account not found")).
                    getFirstName();

            System.out.println(firstNameInfo);
            firstName.setText(firstNameInfo);
        }
        reservationPanel.add(firstName);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        lastNameLabel.setBounds(20, 90, 100, 20);
        reservationPanel.add(lastNameLabel);
        lastName = new JTextField();
        lastName.setFont(new Font("Arial", Font.PLAIN, 15));
        lastName.setBounds(170, 90, 190, 20);
        if(SessionManager.getInstance().getCurrentAccount().getRole() ==Role.GUEST) {
            String lastNameInfo = controller.getAccount(SessionManager.getInstance().
                            getCurrentAccount().
                            getEmail()).
                    orElseThrow(() -> new NoSuchElementException("Reservation not found")).
                    getLastName();
            lastName.setText(lastNameInfo);
        }
        reservationPanel.add(lastName);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        emailLabel.setBounds(20, 120, 100, 20);
        reservationPanel.add(emailLabel);
        email = new JTextField();
        email.setFont(new Font("Arial", Font.PLAIN, 15));
        email.setBounds(170, 120, 190, 20);
        if(SessionManager.getInstance().getCurrentAccount().getRole() ==Role.GUEST) {
            String emailInfo = controller.getAccount(SessionManager.getInstance().
                            getCurrentAccount().
                            getEmail()).
                    orElseThrow(() -> new NoSuchElementException("Reservation not found")).
                    getEmail();
            email.setText(emailInfo);
        }
        reservationPanel.add(email);

        JLabel guestsNumberLabel = new JLabel("Guests Number:");
        guestsNumberLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        guestsNumberLabel.setBounds(20, 150, 120, 20);
        reservationPanel.add(guestsNumberLabel);
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 8, 1);
        guestNumber = new JSpinner(spinnerModel);
        guestNumber.setBounds(170, 150, 190, 20);
        reservationPanel.add(guestNumber);

        JLabel checkInLbl = new JLabel("Check-In:");
        checkInLbl.setFont(new Font("Arial", Font.PLAIN, 15));
        checkInLbl.setBounds(20, 180, 120, 20);
        reservationPanel.add(checkInLbl);
        checkInDatePicker = new DatePicker();
        checkInDatePicker.setBounds(170, 180, 190, 20);
        checkInDatePicker.setDate(checkIn);
        checkInDatePicker.setPreferredSize(new Dimension(200, 30));
        reservationPanel.add(checkInDatePicker);
        checkInDatePicker.setEnabled(false);

        JLabel checkOutLbl = new JLabel("Check-Out:");
        checkOutLbl.setFont(new Font("Arial", Font.PLAIN, 15));
        checkOutLbl.setBounds(20, 210, 120, 20);
        reservationPanel.add(checkOutLbl);
        checkOutDatePicker = new DatePicker();
        checkOutDatePicker.setBounds(170, 210, 190, 20);
        checkOutDatePicker.setDate(checkOut);
        checkOutDatePicker.setPreferredSize(new Dimension(200, 30));
        reservationPanel.add(checkOutDatePicker);
        checkOutDatePicker.setEnabled(false);
        roomId.setEditable(false);


        c.add(reservationPanel);

        submitButton = new JButton("Confirm");
        submitButton.setBounds(200, 400, 100, 40);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToCSV();
            }
        });
        add(submitButton);
    }

    public void saveToCSV() {
        String csvFileName = "src/main/resources/ReservationList.csv";
        // Extracting the reservation data from the form
        int roomNumber = Integer.parseInt(roomId.getText());
        String guestFirstName = firstName.getText();
        String guestLastName = lastName.getText();
        String guestEmail = email.getText();
        int numberOfGuests = (int) guestNumber.getValue();

        Date startDate = java.sql.Date.valueOf(checkInDatePicker.getDate());
        Date endDate = java.sql.Date.valueOf(checkOutDatePicker.getDate());

        if (guestFirstName.isEmpty() || guestLastName.isEmpty() || guestEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }
        try {
            ReservationController controller = new ReservationController(new ReservationJDBCDAO());
            SaleController saleController = new SaleController(new SaleJDBCDAO());

            RoomController roomController = new RoomController(new RoomJDBCDAO());

            GuestAccountController guestAccountController = new GuestAccountController(new GuestAccountJDBCDAO());


            Reservation res = new Reservation(roomNumber, guestFirstName, guestLastName, guestEmail, numberOfGuests, startDate, endDate, false);



            long differenceMillis = res.getEndDate().getTime() - res.getStartDate().getTime();
            long daysApart = differenceMillis / (1000 * 60 * 60 * 24);



            Sale sale = new Sale(res.getStartDate(),
                    String.valueOf(roomController.getRoom(roomNumber).orElseThrow().getRoomNumber()),
                    roomController.getRoom(roomNumber).orElseThrow().getPrice(), (int) daysApart);

            if (SessionManager.getInstance().getCurrentAccount().getRole() == Role.GUEST) {
                if(!controller.hasConflictingReservations(res.getRoomNumber(),checkInDatePicker.getDate(), checkOutDatePicker.getDate())){
                    sale.setAccountId(SessionManager.getInstance().getCurrentAccount().getId());
                    saleController.insertSale(sale);
                    controller.insertReservation(res);
                    JOptionPane.showMessageDialog(this, "Reservation saved successfully.");
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Reservation times conflict");
                }

            } else {

                if(!controller.hasConflictingReservations(res.getRoomNumber(),checkInDatePicker.getDate(), checkOutDatePicker.getDate())){
                    int acctId = guestAccountController.getAccount(guestEmail).orElseThrow(() ->
                            new NoSuchElementException("Guest not found")).getId();
                    sale.setAccountId(acctId);
                    saleController.insertSale(sale);
                    controller.insertReservation(res);
                    JOptionPane.showMessageDialog(this, "Reservation saved successfully.");
                    dispose();
                }
                else{
                    JOptionPane.showMessageDialog(null,"Reservation times conflict");
                }


            }



        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Reservation failed to save! ");
        }
    }

    private String formatDate(java.util.Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }
}

