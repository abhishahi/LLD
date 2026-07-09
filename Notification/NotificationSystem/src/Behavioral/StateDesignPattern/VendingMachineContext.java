package Behavioral.StateDesignPattern;

public interface VendingMachineContext {

    void setState(State state);
    public void request() ;
    public void insertMoney(double amount);
    public void selectProduct(String product);
}
