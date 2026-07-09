package Behavioral.VisitorDesignPattern;

public class File implements FileElement{

    private final String fileName;
    private final int fileSize;

    public File(String name, int size) {
        this.fileName = name;
        this.fileSize = size;
    }
    public String getFileName() {
        return fileName;
    }
    public int getFileSize() {
        return fileSize;
    }
    public void accept(FileOperationVisitor visitor){
        visitor.visit(this);
    }
}
