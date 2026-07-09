package Behavioral.StateDesignPattern;


public class PaymentProcess implements State{
   private final double amount;
    public PaymentProcess( double amount)
    {
        this.amount = amount;
    }
    public void handleRequest(VendingMachineContext context) {
        System.out.println("Money inserted: $" + amount);
        System.out.println("Payment Process State: Processing payment...");
        // Simulate payment processing
        System.out.println("Payment of $" + amount + " processed successfully.");
    }
}
