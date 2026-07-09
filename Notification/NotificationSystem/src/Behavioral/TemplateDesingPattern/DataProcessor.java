package Behavioral.TemplateDesingPattern;

public abstract class DataProcessor {




    boolean isValid ;
    public final void processData() {
        readData();
        if(isValid())
        {
           validateData();
        }
        processDataInternal();
        writeData();
    }

    protected abstract void readData();
    protected abstract void processDataInternal();
    protected abstract void writeData();
    protected abstract void validateData();

    public void setValid(boolean valid) {
        isValid = valid;
    }
    public boolean isValid() {
        return isValid;
    }




}
