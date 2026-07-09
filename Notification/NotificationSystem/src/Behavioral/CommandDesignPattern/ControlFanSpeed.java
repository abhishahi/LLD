package Behavioral.CommandDesignPattern;

public class ControlFanSpeed implements ICommand{
    int speed;
    ControlFanSpeed(int speed)
    {
        this.speed = speed;
    }
    public void execute()
    {
      Fan  fan = new Fan();
      fan.setSpeed(speed);
    }
}
