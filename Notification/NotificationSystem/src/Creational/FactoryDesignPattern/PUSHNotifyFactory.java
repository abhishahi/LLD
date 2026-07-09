package Creational.FactoryDesignPattern;

public class PUSHNotifyFactory implements  OpenCloseNotificationFactory{

  Notification createtNotification(){
        return new PUSHNotification();
    }
}
