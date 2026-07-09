
public class Main {
    public static void main(String[] args) {
        //System.out.println("Hello, World!");
		//NotificationFactory  nFactory  = new NotificationFactory();

       /* Notification email = NotificationFactory.getNotificationSystem("email");

        Notification sms = NotificationFactory.getNotificationSystem("SMS");

        Notification push = NotificationFactory.getNotificationSystem("PUSH");

        assert sms != null;
        sms.notifyUser();
        assert email != null;
        email.notifyUser();

        assert push != null;
        push.notifyUser(); */

        OpenCloseNotificationFactory OCFactory = new PUSHNotifyFactory();
        OCFactory.getNotificationObj();

    }
}