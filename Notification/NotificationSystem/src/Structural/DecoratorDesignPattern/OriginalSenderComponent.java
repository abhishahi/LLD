package Structural.DecoratorDesignPattern;

public class OriginalSenderComponent implements Notifier{
    @Override
    public void send(String message) {
        System.out.println("Original Sender: " + message);
    }
}
