package networking.client;

import networking.IMessage;
import networking.INetworkEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface IClient extends INetworkEntity {


    /**
     * connects to a server and throws an ioException if an error occurs during the connection
     * @param address address of the server
     * @param port port of the server
     * @throws IOException
     */
    void connect(String address, int port) throws IOException;

    /**
     * closes the connection to the server
     */
    void stop();

    /**
     * Returns the received messages from the server
     * @return the received messages as list
     */
    List<IMessage> getMessages();

    /**
     * sends a message to the server
     * @param message message to send
     */
    void sendMessage(IMessage message);


    /**
     * returns the thread of the client
     * @return thread of the client
     */
    Thread getThread();

}
