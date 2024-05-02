package org.bearluxury.Email;

import org.bearluxury.Email.EmailSender;
import org.junit.Test;

import java.util.Date;


public class EmailSenderTest {
    @Test
    public void sendingEmailTest(){
        EmailSender.sendAccountCreationEmail("Nick","Nicholas_Nolen1@baylor.edu");
    }
    @Test
    public void sendingReservationEmailTest(){
        Date date1 = new Date();
        EmailSender.sendReservationConfirmation("Nick","Nicholas_Nolen1@baylor.edu", date1, date1);
    }

}
