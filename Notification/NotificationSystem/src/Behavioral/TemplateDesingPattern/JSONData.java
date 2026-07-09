package Behavioral.TemplateDesingPattern;

public class JSONData extends DataProcessor {

    @Override
    protected void readData() {
        setValid(true);
        System.out.println("Reading JSON Data");
    }

    @Override
    protected void processDataInternal() {
        System.out.println("Processing JSON Data");
    }
    @Override
    protected void validateData() {
        System.out.println("Validated JSON Data");
    }


    @Override
    protected void writeData() {
        System.out.println("Writing JSON Data");
    }
}
