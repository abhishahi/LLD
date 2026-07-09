package Behavioral.StrategyDesignPattern_Behave;

import Behavioral.StrategyDesignPattern_Behave.Strategy.PayementStrategy;

public class PaymentGateway {

    private PayementStrategy strategy;
    PaymentGateway(PayementStrategy strategy)
    {
        this.strategy=strategy;
    }
    void wayOfPayment()
    {
        strategy.pay();
        //System.out.println("Payment done via Payment Gateway" );
    }
}
