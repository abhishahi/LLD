package Behavioral.CommandDesignPattern;

public class Client {

    public static void main(String[] args) {

        Remote remote = new Remote();

        ICommand turnOnFan = new TurnOnFan();
        ICommand turnOffFan = new TurnOffFan();
        ICommand controlFanSpeed= new ControlFanSpeed(5);

        remote.setCommand(turnOnFan).pressButton();
        remote.setCommand(controlFanSpeed).pressButton();
        remote.setCommand(turnOffFan).pressButton();


        ICommand turnOnBulb = new TurnOnBulb();
        ICommand turnOffBulb = new TurnOffBulb();

        remote.setCommand(turnOnBulb).pressButton();
        remote.setCommand(turnOffBulb).pressButton();


    }
}
