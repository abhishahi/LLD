class NotificationFactory
{
    public static Notification getNotificationSystem(String type)
    {
        if(type.equalsIgnoreCase("EMAIL"))
        {
            return new EmailNotification();
        } else if (type.equalsIgnoreCase("SMS")) {
            return new SMSNotification();
        }
        else if (type.equalsIgnoreCase("PUSH")) {
            return new PUSHNotification();
        }
        else
          //  return null;
          throw new IllegalArgumentException("Unsupported object creation");

    }


}