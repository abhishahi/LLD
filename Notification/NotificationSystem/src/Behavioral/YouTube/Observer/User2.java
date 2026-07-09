package Behavioral.YouTube.Observer;

import Behavioral.YouTube.Channel;

public class User2 implements Subscriber {

    private final  Channel channel;

    public User2(Channel channel) {
        this.channel = channel;
    }
    public void update() {

        System.out.println("Hey Om, New Video Uploaded by " + channel.getChannelName() + " channel");
    }
}
