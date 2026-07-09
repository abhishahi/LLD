package Behavioral.TemplateDesingPattern;

public class XMLData extends  DataProcessor{

    @Override
    protected void readData() {
        setValid(true);
        System.out.println("Reading XML Data");
    }
    @Override
    protected void validateData() {

        System.out.println("Validated XML Data");
    }

    @Override
    protected void processDataInternal() {
        System.out.println("Processing XML Data");
    }

    @Override
    protected void writeData() {
        System.out.println("Writing XML Data");
    }
}
