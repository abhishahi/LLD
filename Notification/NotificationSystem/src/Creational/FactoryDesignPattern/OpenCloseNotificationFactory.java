package Creational.FactoryDesignPattern;


public abstract class OpenCloseNotificationFactory {
    abstract Notification createtNotification();

    public void getNotificationObj(){
        Notification notify = createtNotification();
        notify.notifyUser();
    }
}
