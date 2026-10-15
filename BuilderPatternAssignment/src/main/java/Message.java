public class Message {

    private final MessageType messageType;
    private final String content;
    private final String sender;
    private final String recipient;
    private final boolean isDelivered;
    private final long timestamp;

    public Message(MessageBuilder builder) {
        this.messageType = builder.messageType;
        this.content = builder.content;
        this.sender = builder.sender;
        this.recipient = builder.recipient;
        this.isDelivered = builder.isDelivered;
        this.timestamp = builder.timestamp;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public static class MessageBuilder
    {
        private  MessageType messageType;
        private  String content;
        private  String sender;
        private  String recipient;
        private  boolean isDelivered;
        private  long timestamp;  

        
        public MessageBuilder(MessageType messageType, String content, String sender, String recipient) {
            this.messageType = messageType;
            this.content = content;
            this.sender = sender;
            this.recipient = recipient;
        }
        public MessageBuilder isDelivered(boolean isDelivered) {
            this.isDelivered = isDelivered;
            return this;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((messageType == null) ? 0 : messageType.hashCode());
            result = prime * result + ((content == null) ? 0 : content.hashCode());
            result = prime * result + ((sender == null) ? 0 : sender.hashCode());
            result = prime * result + ((recipient == null) ? 0 : recipient.hashCode());
            result = prime * result + (isDelivered ? 1231 : 1237);
            result = prime * result + (int) (timestamp ^ (timestamp >>> 32));
            return result;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            MessageBuilder other = (MessageBuilder) obj;
            if (messageType != other.messageType)
                return false;
            if (content == null) {
                if (other.content != null)
                    return false;
            } else if (!content.equals(other.content))
                return false;
            if (sender == null) {
                if (other.sender != null)
                    return false;
            } else if (!sender.equals(other.sender))
                return false;
            if (recipient == null) {
                if (other.recipient != null)
                    return false;
            } else if (!recipient.equals(other.recipient))
                return false;
            if (isDelivered != other.isDelivered)
                return false;
            if (timestamp != other.timestamp)
                return false;
            return true;
        }
        public MessageBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

    

        public Message build() {
            return new Message(this);
        }

    }
}