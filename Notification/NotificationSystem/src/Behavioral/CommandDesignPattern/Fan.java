package Behavioral.CommandDesignPattern;

public class Fan implements Appliances{
    boolean isOn;
    int speed;
    public void turnOn()
    {
        isOn = true;
        System.out.println("Fan is turned ON");
    }
    public void turnOff()
    {
        isOn = false;
        System.out.println("Fan is turned OFF");
    }
    public void setSpeed(int speed)
    {
        this.speed = speed;
        System.out.println("Fan speed is set to " + speed);
    }
}
