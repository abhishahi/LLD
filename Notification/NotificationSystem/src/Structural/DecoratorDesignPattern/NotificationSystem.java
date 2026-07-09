package Structural.DecoratorDesignPattern;

public class NotificationSystem {

    public static void main(String[] args) {

                Notifier notifier = new WhatsAppDecorator(new SMSDecorator(new OriginalSenderComponent())) ;


                notifier.send("Hello, this is a notification message!");
    }
}
