package Behavioral.MomentoDesignPattern;

public class TextMangementSystem {
    public static void main(String[] args) {

        TextEditor textEditor = new TextEditor();
        History history = new History();
        textEditor.setText("Hi Shubham");
        history.insert(textEditor.save());
        System.out.println("Current Text: "+textEditor.getText());

        textEditor.setText("Hi Abhishek");
        history.insert(textEditor.save());
        System.out.println("Current Text: "+textEditor.getText());

        textEditor.setText("Hi Both");
        System.out.println("Current Text: "+textEditor.getText());
        Momento prevMomento = history.retrive();
        textEditor.restore(prevMomento);
        System.out.println("Undo Text: "+textEditor.getText());

        Momento prevMomento1 = history.retrive();
        textEditor.restore(prevMomento1);
        System.out.println("Undo Text: "+textEditor.getText());
    }
}
