package Behavioral.StrategyDesignPattern_Behave.Strategy;

public class CreditPaymentStrategy implements PayementStrategy{

    @Override
    public void pay() {
        System.out.println("Payment done via credit card");
    }
}
