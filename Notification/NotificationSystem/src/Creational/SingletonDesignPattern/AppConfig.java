package Creational.SingletonDesignPattern;

public class AppConfig {

    public static void main(String[] args) {
        //AppSingleConfig config1 = AppSingleConfig.getInstance();
        //AppSingleConfig config2 = AppSingleConfig.getInstance();

        AppSinglePUBConfig config1 = AppSinglePUBConfig.getInstance();
        AppSinglePUBConfig config2 = AppSinglePUBConfig.getInstance();
        System.out.println("Are both instances the same? " + (config1 == config2));
    }
}
