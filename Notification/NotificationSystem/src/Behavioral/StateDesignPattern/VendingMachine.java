package Behavioral.StateDesignPattern;

public class VendingMachine implements VendingMachineContext{

    private State state;
    private double amount;
    private String product;

    public VendingMachine() {
        this.state = new ReadyToRock();
        this.amount = 0.0;
        this.product = "";
    }
    public void setState(State state) {
        this.state = state;
    }
    public void request() {
        state.handleRequest(this);
    }

    public void insertMoney(double amount) {
        this.amount = amount;
        setState(new PaymentProcess(amount));
        request();
    }
    public void selectProduct(String product) {
        this.product = product;
        setState(new ProductSelection(product));
        request();

    }
}
