package Behavioral.VisitorDesignPattern;

public interface FileElement {

    public void accept(FileOperationVisitor visitor);
}
