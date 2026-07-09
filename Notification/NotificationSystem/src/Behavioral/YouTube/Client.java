package Behavioral.YouTube;

import Behavioral.YouTube.Observer.Subscriber;
import Behavioral.YouTube.Observer.User1;
import Behavioral.YouTube.Observer.User2;

public class Client {
    public static void main(String[] args) {
        // Create a channel
       Channel mainChannel = new ShahiCodeWithLove();
        // Create subscribers
        Subscriber subscriber1 = new User1(mainChannel);
        Subscriber subscriber2 = new User2(mainChannel);

         mainChannel.subscribe(subscriber1);
         mainChannel.subscribe(subscriber2);
         mainChannel.uploadVideo("Observer Design Pattern in Java");

        // Unsubscribe a subscriber
         mainChannel.unSubscribe(subscriber1);
        mainChannel.uploadVideo("Strtegy Design Pattern in Java");


        // Create a channel
        Channel mainChannel1 = new ShubhamSystemDesignwithMind();
        // Create subscribers
        Subscriber subscriber3 = new User1(mainChannel1);
        Subscriber subscriber4 = new User2(mainChannel1);

        mainChannel1.subscribe(subscriber3);
        mainChannel1.subscribe(subscriber4);
        mainChannel1.uploadVideo("Singleton Design Pattern in Java");

        // Unsubscribe a subscriber
        mainChannel1.unSubscribe(subscriber4);
        mainChannel1.uploadVideo("Factory Design Pattern in Java");



    }
}
