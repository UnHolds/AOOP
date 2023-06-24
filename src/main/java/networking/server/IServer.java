package networking.server;

import networking.IMessage;
import networking.INetworkEntity;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface IServer extends INetworkEntity, Runnable{

    /**
     * PRE: IServer object is not null, port is a valid port (>0)
     * POST: Starts the server on the given port and accepts infinitely many clients,
     * until the startGame() method is called. This function will not hold until all clients are connected.
     * Rather it creates a new thread and dispatches it. If the server cannot be started a IOException is thrown
     *
     * @param port the port on which the server should listen on
     * @throws IOException
     */
    void start(int port) throws IOException;

    /**
     * PRE: IServer object is not null, start was called successfully earlier
     * POST: Starts the game, that means that no new clients will be accepted,
     * and that the mainServerLoop will be executed
     *
     * @param startMessages the first messages that will be sent to all clients
     */
    void startGame(List<IMessage> startMessages);

    /**
     * PRE: IServer object is not null, start server and start game have been called successfully
     * POST: Notifies server that a serverClient has received a new message
     */
    void notifyNewMessages();

    /**
     * PRE:  IServer object is not null, IServer is running
     * POST: Server is stopped and server clients are stopped, all sockets will be closed
     *
     * @throws IOException
     */
    void stop() throws IOException;

    /**
     * PRE: IServer object is not null, IServer is running
     * POST: Returns all the connected serverClients or null
     *
     * @return the connected serverClients or null
     */
    List<IServerClient> getClients();

    /**
     * PRE: IServer object is not null, IServer is running, message is not null
     * POST: Sends the given message to all connected serverClients
     *
     * @param message message to be sent
     */
    void sendToAllClients(IMessage message);

    /**
     * PRE: IServer object is not null, IServer is running, message queue has been initialized
     * POST: Returns all the received messages and clears the stored received message list
     *
     * @return all the received messages
     */
    BlockingQueue<IMessage> getMessageQueue();

    /**
     * PRE: IServer object is not null, IServer is running
     * POST: Returns the thread in which the server is running
     *
     * @return the server thread
     */
    Thread getThread();

    /**
     * PRE: IServer object is not null, IServer is running
     * POST: Send a message to all connected clients if a new client has joined
     */
    void sendUpdateConnectedClientsMessage();
}
