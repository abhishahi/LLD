package Creational.PrototypeDesignPattern;

/**
 * Implements the Clone interface to provide deep cloning functionality for InterierManager objects.
 */
public class InterierManagerClone implements Clone<InterierManager> {

    /**
     * Creates a deep copy of the provided InterierManager object.
     *
     * @param object the InterierManager object to be cloned
     * @return a deep copy of the provided InterierManager object
     */
    @Override
    public InterierManager copy(InterierManager object) {
        InterierManager interierManager2 = new InterierManager();
        interierManager2.setInterierId(object.getInterierId());
        for (Interier intr : object.getInterierList()) {
            interierManager2.getInterierList().add(intr.clone()); // Use the clone method for deep copy
        }
        return interierManager2;
    }
}
