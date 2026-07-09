package Behavioral.ChainOfResponsibility;

public class username extends ValidateHandler {
    public username(ValidateHandler next) {
        super(next);
    }
    @Override
    public void validate(Source source)
    {
        //validate email
        //abc@gmail.com
        if(source.getUsername()!= null) {
            if (source.getUsername().length()< 5) {
                throw new IllegalArgumentException("Username must be at least 5 characters");
            }
            else
            {
                System.out.println("UserName validated");
                super.validate(source);
            }
        }


    }
}
