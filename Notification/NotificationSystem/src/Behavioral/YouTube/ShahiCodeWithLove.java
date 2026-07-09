package Behavioral.YouTube;

import Behavioral.YouTube.Observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class ShahiCodeWithLove implements Channel {
    private List<Subscriber> subs = new ArrayList<>();
    private String channelName = null;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }





    public void subscribe(Subscriber subscriber)
    {
        subs.add(subscriber);
        System.out.println("New Subscriber added");
    }
    public void unSubscribe(Subscriber subscriber)
    {
        subs.remove(subscriber);
        System.out.println("Subscriber removed");
    }
    public void notifySubscribers()
    {
        for(Subscriber sub:subs)
        {
            sub.update();
        }
    }
    public void uploadVideo(String title)
    {
        System.out.println("New Video Uploaded: "+title);
        setChannelName("ShahiCodeWithLove");
        notifySubscribers();
    }

}
