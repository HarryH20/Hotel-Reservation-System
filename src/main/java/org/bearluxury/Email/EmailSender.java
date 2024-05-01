package org.bearluxury.Email;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import java.util.Date;


/**
 * Utility class for sending emails related to account creation and reservation confirmation.
 *
 * @author Will Clore
 * @author Harrsion Hassler
 * @author Derek Martinez
 * @author Nicholas Nolen
 * @author Joseph Zuniga
 * @author Alan Vilagrand
 */
public class EmailSender {

    /**
     * Sends an account creation confirmation email to the specified recipient.
     *
     * @param recipient the name of the recipient
     * @param emailAddress the email address of the recipient
     */
    public static void sendAccountCreationEmail(String recipient, String emailAddress) {
        // Constructing the email message
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

        // Sending the email using SMTP
        MailerBuilder
                .withSMTPServer("mail.smtp2go.com", 2525, "nicholas_nolen1@baylor.edu", "xltlHLbGWIM6Q6iB")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(email);
    }

    /**
     * Sends a reservation confirmation email to the specified recipient.
     *
     * @param recipient the name of the recipient
     * @param emailAddress the email address of the recipient
     * @param startDate the start date of the reservation
     * @param endDate the end date of the reservation
     */
    public static void sendReservationConfirmation(String recipient, String emailAddress, Date startDate, Date endDate) {
        // Constructing the email message
        Email email = EmailBuilder.startingBlank()
                .from("Baylor Bear Luxury", "nicholas_nolen1@baylor.edu")
                .to(recipient, emailAddress)
                .withSubject("Baylor Bear Luxury- Reservation Confirmation")
                .withPlainText("""
                        Thank you for Booking with us!
                        We are so excited to have you stay with us!
                        Your reservation has been set for: """ + startDate + ", Until: " + endDate + " \r \n" +
                        "With gracious regards, \r \n" +
                        "Baylor Bear Luxury staff")
                .buildEmail();

        // Sending the email using SMTP
        MailerBuilder
                .withSMTPServer("mail.smtp2go.com", 2525, "nicholas_nolen1@baylor.edu", "xltlHLbGWIM6Q6iB")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .buildMailer()
                .sendMail(email);
    }
}
