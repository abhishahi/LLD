package Behavioral.CommandDesignPattern;

public class TurnOnFan implements ICommand {

    public void execute()
    {
        Fan fan = new Fan();
        fan.turnOn();
    }
}
