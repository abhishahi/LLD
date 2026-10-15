public class Main {
  public static void main(String[] args) {
    Message textMessage = new Message.MessageBuilder(MessageType.TEXT, "Hello!", "Alice", "Bob")
      .isDelivered(true)
      .timestamp(System.currentTimeMillis())
      .build();

    Message imageMessage = new Message.MessageBuilder(MessageType.IMAGE, "image.jpg", "Bob", "Alice")
      .timestamp(System.currentTimeMillis())
      .build();
  }
}