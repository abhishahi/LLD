package Behavioral.StateDesignPattern;

public class ReadyToRock  implements State{

    public void handleRequest(VendingMachineContext context) {
        System.out.println("Welcome to Vending Machine...");
        System.out.println("Ready to serve you ");
    }
}
