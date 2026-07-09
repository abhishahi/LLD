package Behavioral.MediatorDesignPattern;

public interface Bidders {

    public void placeBid(String Name, double amount);
    public void receiveBid(String Name,double amount);
    public String getName();
}
