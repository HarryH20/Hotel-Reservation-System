package org.bearluxury;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Main {
    private static DAO<Reservation> dao;

    public Main(DAO<Reservation> dao) {
        this.dao = dao;
    }


    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "Ninja123!")) {
            System.out.println("Connection is valid");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ApplicationContext context = SpringApplication.run(Main.class, args);
        System.out.println("Create reservations ---------");
        Date date1 = new Date(100);
        Date date2 = new Date(110);
        Reservation reservation1 = new Reservation(100, "Harrison", date1, date2, 1.0);
        dao.create(reservation1);

        System.out.println("One reservation----------");
        Optional<Reservation> reservation2 = dao.get(100);
        System.out.println(reservation2.get());

        reservation1.setGuestName("John");
        dao.update(reservation1, 100);
        dao.delete(200);


        // The code below is now integrated with Spring context
        Reservation reservation3 = context.getBean(Reservation.class);
        reservation3.setRoomNumber(200);
        reservation3.setGuestName("Alice");
        reservation3.setStartDate(new Date(120));
        reservation3.setEndDate(new Date(130));
        reservation3.setDiscount(0.9);
        dao.create(reservation3);

        System.out.println("All reservations -----------");
        List<Reservation> reservations = dao.list();
        reservations.forEach(System.out::println);


    }
}
