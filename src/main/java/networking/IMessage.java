package networking;

import java.io.IOException;

public interface IMessage {


    String toBase64String() throws IOException;

    MessageType getMessageType();

}
