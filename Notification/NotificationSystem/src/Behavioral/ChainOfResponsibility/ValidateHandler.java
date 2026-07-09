package Behavioral.ChainOfResponsibility;

//Validate email, password, age, and username uniqueness.
public abstract class ValidateHandler {

    private ValidateHandler nextValidateHandler;

    ValidateHandler(ValidateHandler validateHandler)
    {
        nextValidateHandler = validateHandler;
    }

    public void validate(Source source)
    {
        if(nextValidateHandler != null)
        {
            nextValidateHandler.validate(source);
        }
    }
}
