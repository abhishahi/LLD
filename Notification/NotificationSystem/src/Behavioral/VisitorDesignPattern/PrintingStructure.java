package Behavioral.VisitorDesignPattern;

public class PrintingStructure  implements  FileOperationVisitor{
    @Override
    public void visit(File file) {
        System.out.println("Visiting file: " + file.getFileName());
    }

    @Override
    public void visit(Directory directory) {
        System.out.println("Visiting directory: " + directory.getDirName());
    }
}
