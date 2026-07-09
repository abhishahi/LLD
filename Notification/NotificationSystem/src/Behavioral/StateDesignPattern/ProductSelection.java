package Behavioral.StateDesignPattern;

public class ProductSelection implements State{

    private final String product;
    ProductSelection(String product) {
        this.product = product;
    }
    public void handleRequest(VendingMachineContext context) {
        System.out.println("Please select the product to buy...");
        System.out.println("Product selected: " + product);
        context.setState(new ProductDispense());
        context.request();
    }
}
