package Creational.SingletonDesignPattern;

public class AppSingleConfig {

    private static AppSingleConfig instance;

    private AppSingleConfig(){}

    public static AppSingleConfig getInstance()
    {
        if(instance == null)
        {
            synchronized(AppSingleConfig.class) {
                if (instance == null) {
                    instance = new AppSingleConfig();
                }
            }
        }
        return instance;
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
