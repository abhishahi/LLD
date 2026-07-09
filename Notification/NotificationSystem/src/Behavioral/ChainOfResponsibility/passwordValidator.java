package Behavioral.ChainOfResponsibility;

public class passwordValidator extends ValidateHandler{
    public passwordValidator(ValidateHandler next) {
        super(next);
    }
    @Override
    public void validate(Source source)
    {
        //validate email
        //abc@gmail.com
        if(source.getPassword()!= null) {
            if (source.getPassword().length()< 8) {
                throw new IllegalArgumentException("Password must be at least 8 characters");
            }
            else
            {
                System.out.println("Password validated");
                super.validate(source);
            }
        }


    }
}
