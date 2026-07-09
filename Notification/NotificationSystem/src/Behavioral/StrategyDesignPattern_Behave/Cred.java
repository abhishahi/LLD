package Behavioral.StrategyDesignPattern_Behave;

import Behavioral.StrategyDesignPattern_Behave.Strategy.CreditPaymentStrategy;

public class Cred extends PaymentGateway{


    Cred()
    {
        super(new CreditPaymentStrategy());
    }
}
