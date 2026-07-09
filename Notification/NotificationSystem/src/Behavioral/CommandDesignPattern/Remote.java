package Behavioral.CommandDesignPattern;

public class Remote {

    private ICommand command;

    public Remote setCommand(ICommand command)
    {
        this.command = command;
        return this;
    }

    public void pressButton()
    {
        command.execute();
    }
}
