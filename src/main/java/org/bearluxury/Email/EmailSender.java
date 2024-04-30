package org.bearluxury.Email;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import java.util.Date;

//
public class EmailSender {
    public static void sendAccountCreationEmail(String recipient,String emailAddress ){
        Email email = EmailBuilder.startingBlank()
                .from("Baylor Bear Luxury", "nicholas_nolen1@baylor.edu")
                .to(recipient, emailAddress)
                .withSubject("Baylor Bear Luxury- Account Confirmation")
                .withPlainText("""
                        Thank you for Registering!
                        We are so excited to have you stay with us!

                        With gracious regards,
                        Baylor Bear Luxury staff""")
                .buildEmail();

        MailerBuilder
                .withSMTPServer("mail.smtp2go.com", 2525, "nicholas_nolen1@baylor.edu", "xltlHLbGWIM6Q6iB")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(email);
    }
    public static void sendReservationConfirmation(String recipient,String emailAddress, Date startDate, Date endDate){
        Email email = EmailBuilder.startingBlank()
                .from("Baylor Bear Luxury", "nicholas_nolen1@baylor.edu")
                .to(recipient, emailAddress)
                .withSubject("Baylor Bear Luxury- Reservation Confirmation")
                .withPlainText("""
                        Thank you for Booking with us!
                        We are so excited to have you stay with us!
                        Your reservation has been set for:\s""" + startDate + ", Until: " + endDate + " \r \n" +
                        "With gracious regards, \r \n" +
                        "Baylor Bear Luxury staff")
                .buildEmail();

        MailerBuilder
                .withSMTPServer("mail.smtp2go.com", 2525, "nicholas_nolen1@baylor.edu", "xltlHLbGWIM6Q6iB")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(email);
    }
}
