import org.bearluxury.CreditCard;
import org.bearluxury.Payment;

public class CreditCardPayment extends Payment {
   CreditCard card;

    public CreditCardPayment(double amount, CreditCard card) {
        super(amount);
        this.card = card;
    }

    @Override
    public void processPayment() {
    }
}
