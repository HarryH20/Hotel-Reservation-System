package org.bearluxury;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class,args);

        reservationRepo repo = context.getBean(reservationRepo.class);
        for(int i = 0; i < 10; i++) {
            Reservation reservation1 = context.getBean(Reservation.class);
            reservation1.setRoomNumber(100);
            reservation1.setGuestName("Ted");
            reservation1.setStartDate(new Date(100));
            reservation1.setEndDate(new Date(200));
            reservation1.setDiscount(1);
            repo.save(reservation1);
        }



        repo.findAll();


    }
}
