package networking.server;

import networking.IMessage;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface IServerClient {

    /**
     * dispatches the server client to a new thread (listen to new messages thread)
     */
    void dispatch();

    /**
     * returns the thread in which the serverClient is currently running in
     * @return the thread of the serverClient
     */
    Thread getThread();

    /**
     * stops the serverClient and closes it socket
     */
    void stop();

    /**
     * returns the received messages of the serverClient. When this function is called it
     * will delete the messages afterwards. So it is more like a get and clear method.
     * @return the received messages since the last getMessage() call
     */
    List<IMessage> getMessages();

    /**
     * sends the given message to the connected client
     * @param message the message to send
     * @throws IOException
     */
    void sendMessage(IMessage message) throws IOException;

    /**
     * returns the address (IP) of the connected client
     * @return ip of the connected client
     */
    String getAddress();

    /**
     * returns whether the client is disconnected or not
     * @return whether the client is disconnected or not
     */
    boolean isDisconnected();

    String getName();

    String getId();
}
