package Creational.SingletonDesignPattern;

public class AppSinglePUBConfig {

    private static class BillPughSingleton {

        public static final AppSinglePUBConfig instance = new AppSinglePUBConfig();
    }
    private AppSinglePUBConfig(){}

    public static AppSinglePUBConfig getInstance()
    {
        return BillPughSingleton.instance;
    }
}
