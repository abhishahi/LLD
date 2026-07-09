package Behavioral.StateDesignPattern;

public class Client {

    public static void main(String[] args) {
        VendingMachineContext vendingMachine = new VendingMachine();
        vendingMachine.insertMoney(5.00);
        vendingMachine.selectProduct("Soda");
    }
}
