package Behavioral.MomentoDesignPattern;

import java.util.Stack;

public class History {

    private Stack<Momento> historyStack = new Stack<>();

    public void insert(Momento m)
    {
        historyStack.push(m);
    }
    public Momento retrive()
    {
        if(!historyStack.isEmpty())
        {
            return historyStack.pop();
        }
        return null;
    }
}
