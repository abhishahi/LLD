package Behavioral.StrategyDesignPattern_Behave.Strategy;

public class PayPalPaymentStrategy implements PayementStrategy{

    @Override
    public void pay() {
        System.out.println("Payment done via PayPal");
    }
}
