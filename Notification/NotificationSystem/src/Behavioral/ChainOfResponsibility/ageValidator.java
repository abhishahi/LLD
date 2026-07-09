package Behavioral.ChainOfResponsibility;



public class ageValidator  extends ValidateHandler {

    public ageValidator(ValidateHandler next) {
        super(next);
    }
    @Override
    public void validate(Source source)
    {
        //validate age
        if(source.getAge() > 0) {
            if (source.getAge() < 18) {
                throw new IllegalArgumentException("Age must be at least 18");
            }
            else
            {
                System.out.println("Age validated");
                super.validate(source);
            }
        }

    }
}
