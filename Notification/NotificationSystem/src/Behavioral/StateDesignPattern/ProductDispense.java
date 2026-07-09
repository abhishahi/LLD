package Behavioral.StateDesignPattern;

public class ProductDispense implements State{

    public void handleRequest(VendingMachineContext context) {
        System.out.println("Please collect your product...");
        System.out.println("Thank you for using the Vending Machine!");
    }
}
