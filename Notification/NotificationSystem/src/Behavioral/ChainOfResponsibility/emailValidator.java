package Behavioral.ChainOfResponsibility;

public class emailValidator extends ValidateHandler {

    public emailValidator(ValidateHandler next) {
        super(next);
    }
    @Override
    public void validate(Source source)
    {
        //validate email
        //abc@gmail.com
        if(source.getEmail()!= null) {
            if (!source.getEmail().contains("@")) {
                throw new IllegalArgumentException("Email must contain @");
            }
            else
            {
                System.out.println("Email validated");
                super.validate(source);
            }
        }


    }
}
