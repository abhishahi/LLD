package Behavioral.StrategyDesignPattern_Behave;

public class FlipKart {

    public static void main(String[] args) {

        PaymentGateway paymentGateway = new Gpay();
        paymentGateway.wayOfPayment();

        paymentGateway= new Cred();
        paymentGateway.wayOfPayment();

        paymentGateway = new Binanace();
        paymentGateway.wayOfPayment();
    }
}
