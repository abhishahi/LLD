package Behavioral.VisitorDesignPattern;

public class Directory  implements FileElement{
    private final String dirName;
    private final int dirSize;

    public Directory(String name, int size) {
        this.dirName = name;
        this.dirSize = size;
    }
    public String getDirName() {
        return dirName;
    }
    public int getDirSize() {
        return dirSize;
    }
    public void accept(FileOperationVisitor visitor){
            visitor.visit(this);
    }

}
