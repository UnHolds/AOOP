package networking.client;

import networking.IMessage;
import networking.INetworkEntity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface IClient extends INetworkEntity {


    /**
     * PRE: IClient is not null, provided address is not null
     * POST: Client is connected to server or IOException is thrown if connection is not possible
     *
     * @param address address of the server
     * @param port port of the server
     * @throws IOException
     */
    void connect(String address, int port) throws IOException;

    /**
     * PRE: IClient is not null, there is an existing connection with the server
     * POST: Closes the connection to the server
     */
    void stop();

    /**
     * PRE: IClient is not null, there is an existing connection with the server
     * POST: Returns a list received messages from the server
     *
     * @return the received messages as list
     */
    List<IMessage> getMessages();

    /**
     * PRE: IClient is not null, there is an existing connection with the server
     * POST: Sends the provided message to the server
     *
     * @param message message to send
     */
    void sendMessage(IMessage message);


    /**
     * PRE: IClient is not null, there is an existing connection with the server
     * POST: Returns the IMessage that is the next message received from the server
     *
     * @return next message received from the server
     */
    IMessage take();

    /**
     * PRE: IClient is not null, there is an existing connection with the server
     * POST: Returns the thread in which the client is executed
     *
     * @return thread of the client
     */
    Thread getThread();

}
