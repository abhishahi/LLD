package Behavioral.VisitorDesignPattern;

public class TotalSize implements FileOperationVisitor{

    @Override
    public void visit(File file) {
        // Implementation for calculating total size of a file
        System.out.println("Calculating size of file: " + file.getFileSize());
    }
    public void visit(Directory directory) {
        // Implementation for calculating total size of a directory
        System.out.println("Calculating size of directory: " + directory.getDirSize());
    }
}
