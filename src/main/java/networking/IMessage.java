package networking;

public interface IMessage {

    int SIZE = 512;

    byte[] toBytes();

    MessageType getMessageType();

}
