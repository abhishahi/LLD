package Creational.PrototypeDesignPattern;

public class Customer {

    public static void main(String[] args) {

        InterierManager intManger = new InterierManager();

        intManger.loadEnterier("123",10);
        InterierManagerClone interierManagerClone = new InterierManagerClone();

        InterierManager interierManager2 =(InterierManager) interierManagerClone.copy( intManger);
        interierManager2.getInterierList().get(0).getItems().remove(0);
        System.out.println(intManger.toString());
        System.out.println(interierManager2.toString());





    }
}
