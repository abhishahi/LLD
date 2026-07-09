package Behavioral.StrategyDesignPattern_Behave.Strategy;

public class UPIPaymentStrategy implements PayementStrategy{

    @Override
    public void pay() {
        System.out.println("Payment done via UPI");
    }
}
