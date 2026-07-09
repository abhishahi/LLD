package Behavioral.MediatorDesignPattern;

public class OnlineAuctionSystem {

    public static void main(String[] args) {
        Mediator mediator = new MediatorImpl();
        Bidders bidder1 = new Bidder1(mediator, "Shubham");
        Bidders bidder2 = new Bidder1(mediator, "Shanti");
        Bidders bidder3 = new Bidder1(mediator, "rajesh");
        Bidders bidder4 = new Bidder1(mediator, "Neelam");
        bidder1.placeBid(bidder1.getName(), 100);
        bidder2.placeBid(bidder2.getName(), 101);
        bidder3.placeBid(bidder3.getName(), 151);
        bidder4.placeBid(bidder4.getName(), 201);
        bidder1.placeBid(bidder1.getName(), 501);

    }
}
