package Behavioral.TemplateDesingPattern;

public class Client {
    public static void main(String[] args) {
        DataProcessor csvProcessor = new TextData("Abhishek");
        csvProcessor.processData();

        System.out.println("-----");

        DataProcessor jsonProcessor = new JSONData();
        jsonProcessor.processData();

        System.out.println("-----");


        DataProcessor xmlProcessor = new XMLData();
        xmlProcessor.processData();


    }
}
