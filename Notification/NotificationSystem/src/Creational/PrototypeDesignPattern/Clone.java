package Creational.PrototypeDesignPattern;

/**
 * A generic interface for cloning objects.
 *
 * @param <T> the type of object to be cloned
 */
public interface Clone<T> {

    /**
     * Creates a copy of the current object.
     *
     * @return a new instance that is a copy of the current object
     */
    T copy(T object);
}
