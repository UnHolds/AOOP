package networking.server;

import networking.IMessage;

import java.util.concurrent.ConcurrentLinkedQueue;

public interface IServerClient {

    void dispatch();

    Thread getThread();

    void stop();

    ConcurrentLinkedQueue<IMessage> getMessages();

    void sendMessage(IMessage message);
    void sendMessageNow(IMessage message);
}
