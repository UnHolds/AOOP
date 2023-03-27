package networking.client;

import networking.IMessage;

import java.io.IOException;
import java.util.List;

public interface IClient {

    void connect(String address, int port) throws IOException;
    void close();

    List<IMessage> getMessages();

    void sendMessage(IMessage message);

}
