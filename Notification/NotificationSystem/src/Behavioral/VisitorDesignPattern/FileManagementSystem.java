package Behavioral.VisitorDesignPattern;

public class FileManagementSystem {
    public static void main(String[] args) {

        File file1 = new File("file1.txt", 100);
        FileOperationVisitor fVisitor = new TotalSize();
        FileOperationVisitor pVisitor = new PrintingStructure();
        file1.accept(pVisitor);
        file1.accept(fVisitor);
        Directory dir1 = new Directory("MyDocuments", 500);
        dir1.accept(pVisitor);
        dir1.accept(fVisitor);
    }
}
