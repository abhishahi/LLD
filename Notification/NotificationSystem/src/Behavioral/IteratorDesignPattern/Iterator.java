package Behavioral.IteratorDesignPattern;

public interface Iterator<T>
{
    public boolean isNextAvail();
    public T next();
}