package Behavioral.YouTube;

import Behavioral.YouTube.Observer.Subscriber;

public interface Channel {

    public void subscribe(Subscriber subscriber);
    public void unSubscribe(Subscriber subscriber);
    public void notifySubscribers();
    public void uploadVideo(String title);
    public String getChannelName();
    public void setChannelName(String channelName);

}
