package Behavioral.CommandDesignPattern;

public class Bulb implements  Appliances{

     boolean isOn;
    public void turnOn()
    {
        isOn = true;
        System.out.println("Bulb is turned ON");
    }
    public void turnOff()
    {
        isOn = false;
        System.out.println("Bulb is turned OFF");
    }
}
