package Behavioral.MediatorDesignPattern;

public interface Mediator {
   public void addBidder(Bidders bidder);
   public void placeBid(String Name, double amount);
}
