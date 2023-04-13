package networking.server;

import networking.IMessage;
import networking.INetworkEntity;

import java.io.IOException;
import java.util.List;

public interface IServer extends INetworkEntity, Runnable{

    /**
     * Starts the server on the given port and accepts infinitely many clients,
     * until the startGame() method is called
     * This function will not hold until all clients are connected.
     * Rather it creates a new thread and dispatches it.
     * This dispatch thread can be acquired via the getThread function
     * @param port the port on which the server should listen on
     * @throws IOException
     */
    void start(int port) throws IOException;

    /**
     * starts the game, that means that no new clients will be accepted,
     * and that the mainServerLoop will be executed
     */
    void startGame();

    /**
     * used to notify the server that a serverClient has received a new message
     */
    void notifyNewMessages();

    /**
     * used to stop the server. This function will also stop all ServerClients
     * and close all sockets.
     * @throws IOException
     */
    void stop() throws IOException;

    /**
     * returns all the connected serverClients
     * @return the connected serverClientes
     */
    List<IServerClient> getClients();

    /**
     * This functions sends the given message to all connected serverClients
     * @param message message to be sent
     */
    void sendToAllClients(IMessage message);

    /**
     * returns all the received messages and clears the stored received message list
     * @return all the received messages
     */
    List<IMessage> getAllMessages();

    /**
     * returns the thread in which the server is running in
     * @return the server thread
     */
    Thread getThread();

    void sendUpdateConnectedClientsMessage();
}
