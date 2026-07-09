package Behavioral.StrategyDesignPattern_Behave;

import Behavioral.StrategyDesignPattern_Behave.Strategy.UPIPaymentStrategy;

public class Gpay extends PaymentGateway{

    Gpay()
    {
        super(new UPIPaymentStrategy());
    }
}
