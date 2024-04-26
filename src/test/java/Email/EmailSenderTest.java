package Email;

import org.bearluxury.Email.EmailSender;
import org.junit.Test;



public class EmailSenderTest {
    @Test
    public void sendingEmailTest(){
        EmailSender.sendConfirmationEmail("Nick","will_clore1@baylor.edu");
    }
}
