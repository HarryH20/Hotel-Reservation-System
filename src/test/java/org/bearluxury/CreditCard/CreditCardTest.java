package org.bearluxury.CreditCard;

import org.bearluxury.account.CreditCard;
import org.bearluxury.shop.CreditCardPayment;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CreditCardTest {

    @Test
    public void testConstructor() throws Exception {
        CreditCard creditCard = new CreditCard("4554-4456-9999", "John Doe", "09/24",  "345");

        assertEquals("4554-4456-9999", creditCard.getCardNumber());
        assertEquals("John Doe", creditCard.getCardHolderName());
        assertEquals("09/24", creditCard.getExpDate());
        assertEquals("345", creditCard.getCvv());
        assertEquals(20000.0, creditCard.getBalance());
    }

    @Test
    public void testGoodSubtractBalance() {
        CreditCard creditCard = new CreditCard("4554-4456-9999", "John Doe", "09/24",  "345");
        CreditCardPayment creditCardPayment = new CreditCardPayment(10000.50, creditCard);

        creditCardPayment.processPayment();

        assertEquals(9999.50, creditCard.getBalance());
    }

    @Test
    public void testBadSubtractBalance() {
        CreditCard creditCard = new CreditCard("4554-4456-9999", "John Doe", "09/24",  "345");
        CreditCardPayment creditCardPayment = new CreditCardPayment(40000.0, creditCard);

        creditCardPayment.processPayment();

        assertEquals(20000.0, creditCard.getBalance());
    }

    @Test
    public void testReallySmallFailureSubtractBalance() {
        CreditCard creditCard = new CreditCard("4554-4456-9999", "John Doe", "09/24",  "345");
        CreditCardPayment creditCardPayment = new CreditCardPayment(0.00005, creditCard);

        creditCardPayment.processPayment();

        assertEquals(20000.0, creditCard.getBalance());
    }

    @Test
    public void testDecimalPointSuccessSubtractBalance() {
        CreditCard creditCard = new CreditCard("4554-4456-9999", "John Doe", "09/24",  "345");
        CreditCardPayment creditCardPayment = new CreditCardPayment(10000.08, creditCard);

        creditCardPayment.processPayment();

        assertEquals(9999.92, creditCard.getBalance());
    }
}
