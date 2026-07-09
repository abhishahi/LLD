package Behavioral.CommandDesignPattern;

public class TurnOffBulb implements ICommand{

    public void execute()
    {
        Bulb bulb = new Bulb();
        bulb.turnOff();
    }
}
