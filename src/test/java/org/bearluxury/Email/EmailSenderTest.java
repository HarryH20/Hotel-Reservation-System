package org.bearluxury.Email;

import org.bearluxury.Email.EmailSender;
import org.junit.Test;

import java.util.Date;


public class EmailSenderTest {
    @Test
    public void sendingEmailTest(){
        EmailSender.sendAccountCreationEmail("Will","will_clore1@baylor.edu");
    }
    @Test
    public void sendingReservationEmailTest(){
        Date date1 = new Date();

        EmailSender.sendReservationConfirmation("Alan","Alan_Villagrand1@baylor.edu", date1, date1);
    }

}
