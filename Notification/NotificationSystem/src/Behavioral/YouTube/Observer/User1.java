package Behavioral.YouTube.Observer;

import Behavioral.YouTube.Channel;

public class User1 implements Subscriber{

    private final Channel channel;

    public User1(Channel channel) {
        this.channel = channel;
    }
    public void update() {
        System.out.println("Hey Jay, New Video Uploaded by " + channel.getChannelName() + " channel");
    }
}
