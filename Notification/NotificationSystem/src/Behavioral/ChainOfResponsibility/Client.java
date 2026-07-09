package Behavioral.ChainOfResponsibility;

public class Client {
    public static void main(String[] args) {

        Source source = new Source("abc@gmail.com", "password123", 50, "890");

        ValidateHandler handler = new emailValidator(new passwordValidator(new ageValidator(new username(null))));
        handler.validate(source);
    }
}
