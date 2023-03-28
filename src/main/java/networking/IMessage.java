package networking;

import java.io.IOException;

public interface IMessage {


    String toBase64String() throws IOException;

    MessageType getMessageType();

    String getSenderId();

    String getSenderName();

    long getCurrentGameTick();

}
