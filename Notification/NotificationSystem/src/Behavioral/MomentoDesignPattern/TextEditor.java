package Behavioral.MomentoDesignPattern;

public class TextEditor {

    private String text;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

     public Momento save()
     {
         return new Momento(text);
     }

     public void restore(Momento m)
     {
         this.text = m.getText();
     }



}
