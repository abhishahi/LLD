package Behavioral.VisitorDesignPattern;

public interface FileOperationVisitor {

    public void visit(File file);
    public void visit(Directory directory);
}


