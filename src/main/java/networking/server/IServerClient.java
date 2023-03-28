package networking.server;

import networking.IMessage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface IServerClient {

    void dispatch();

    Thread getThread();

    void stop();

    List<IMessage> getMessages();

    void sendMessage(IMessage message) throws IOException;

    String getAddress();

    boolean isDisconnected();
}
