package networking;

public interface IMessage {


    String toBase64String();

    MessageType getMessageType();

}
