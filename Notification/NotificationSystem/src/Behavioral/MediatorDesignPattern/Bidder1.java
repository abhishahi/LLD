package Behavioral.MediatorDesignPattern;

public class Bidder1 implements Bidders {

    Mediator mediator;
    String Name ;
    double amount;
    public Bidder1(Mediator mediator, String Name) {
        this.mediator = mediator;
        this.Name = Name;
        mediator.addBidder(this);
    }
    public void placeBid(String Name, double amount)
    {
        this.amount = amount;
        System.out.println(Name + " placed bid of amount: " + amount);
        mediator.placeBid(Name, amount);
    }
    public void receiveBid(String Name,double amount)
    {
        System.out.println(this.Name + ":"+ Name +" has placed a bid of amount: " + amount);
    }
    public String getName()
    {
        return Name;
    }

}
