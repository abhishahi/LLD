package Behavioral.CommandDesignPattern;

public class TurnOffFan implements ICommand{
    public void execute()
    {
        Fan fan = new Fan();
        fan.turnOff();
    }
}
