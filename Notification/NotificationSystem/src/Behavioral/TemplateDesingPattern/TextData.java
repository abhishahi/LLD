package Behavioral.TemplateDesingPattern;

public class TextData  extends  DataProcessor{

    private String data;

    public TextData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    protected void readData() {
        setValid(false); // Initially set to false
        System.out.println("Reading Text Data: " + data);
    }
    @Override
    protected void validateData() {
        if (data != null && !data.trim().isEmpty()) {
            System.out.println("Text Data is valid.");
        } else {
            System.out.println("Text Data is invalid.");
        }
    }
    @Override
    protected void processDataInternal() {
    System.out.println("Processing Text Data: " + data.toUpperCase());
}

    @Override
    protected void writeData() {
        System.out.println("Writing Text Data: " + data.toUpperCase());
    }
}