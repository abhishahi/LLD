package Behavioral.StrategyDesignPattern_Behave;

import Behavioral.StrategyDesignPattern_Behave.Strategy.PayPalPaymentStrategy;

public class Binanace extends PaymentGateway {

    Binanace()
    {
        super(new PayPalPaymentStrategy());
    }
}
