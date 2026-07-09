package Structural.DecoratorDesignPattern;

public class WhatsAppDecorator extends SMSDecorator{

    WhatsAppDecorator(Notifier notifier){
        super(notifier);
    }

    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("WhatsApp Decorator: " + message);
    }
}
