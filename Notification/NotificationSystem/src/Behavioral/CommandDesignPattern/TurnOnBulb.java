package Behavioral.CommandDesignPattern;

public class TurnOnBulb implements ICommand{

    public void execute()
    {
        Bulb bulb = new Bulb();
        bulb.turnOn();
    }
}
