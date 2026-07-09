package Behavioral.MediatorDesignPattern;

import java.util.ArrayList;
import java.util.List;

public class MediatorImpl implements Mediator{
    List<Bidders> biddersList;

    public MediatorImpl() {
        biddersList = new ArrayList<>();
    }

    public void addBidder(Bidders bidder)
    {
        if(bidder == null) {
            throw new NullPointerException("Bidder cannot be null");
        }
        if(biddersList.contains(bidder)) {
            return;
        }
        biddersList.add(bidder);
    }
    public void placeBid(String Name, double amount)
    {
        for(Bidders bidder : biddersList)
        {
            if(!bidder.getName().equals(Name)) {
                bidder.receiveBid(Name, amount);
            }
        }
    }
}
