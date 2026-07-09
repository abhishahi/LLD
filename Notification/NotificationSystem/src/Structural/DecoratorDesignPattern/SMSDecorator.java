package Structural.DecoratorDesignPattern;

public class SMSDecorator extends  AbstractSenderDecorator {


    public SMSDecorator(Notifier notifier){
       super(notifier);
    }


    @Override
    public void send(String message) {
        super.send(message);
        System.out.println("SMS Decorator: " + message);
    }
}
