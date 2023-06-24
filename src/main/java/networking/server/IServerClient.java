package networking.server;

import networking.IMessage;
import networking.INetworkEntity;

import java.io.IOException;

public interface IServerClient extends INetworkEntity {

    /**
     * PRE: IServerClient is not null, Server parent is not null
     * POST: Server client is not dispatched to a new thread (listen to new messages thread)
     */
    void dispatch();

    /**
     * PRE: IServerClient is not null, Server parent is not null
     * POST: Returns thread of server client or null if no thread has been dispatched
     *
     * @return the thread of the serverClient
     */
    Thread getThread();

    /**
     * PRE: IServerClient is not null, Server parent is not null
     * POST: Stops the serverClient and closes its socket if it is possible
     */
    void stop();

    /**
     * PRE: IServerClient is not null and has not been stopped, Server parent is not null, message is not null
     * POST: Sends the given message to the connected clients
     *
     * @param message the message to send
     * @throws IOException
     */
    void sendMessage(IMessage message) throws IOException;

    /**
     * PRE: IServerClient is not null and has a connected client
     * POST: Returns the ip address of the connected client
     *
     * @return ip of the connected client
     */
    String getAddress();

    /**
     * PRE: IServerClient is not null
     * POST: Returns true if there is a client connected and false otherwise

     * @return whether the client is disconnected or not
     */
    boolean isDisconnected();

}
