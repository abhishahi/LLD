package Structural.DecoratorDesignPattern;

public abstract class AbstractSenderDecorator implements Notifier{

    protected Notifier notifier;

    AbstractSenderDecorator(Notifier notifier){
        this.notifier = notifier;
    }

    @Override
    public void send(String message) {
        notifier.send(message);
    }
}
