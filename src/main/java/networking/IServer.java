package networking;

import java.io.IOException;

public interface IServer extends Runnable{

    /**
     * Starts the server on the given port and accepts numPlayer many clients.
     * This function will not hold until all clients are connected.
     * Rather it creates a new thread and dispatches it,this thread will then be
     * returned by the function.
     * @param port the port on which the server should listen on
     * @param numPlayers the numbers of players that are allowed to connect
     * @return the created and dispatched thread of the server
     * @throws IOException
     */
    Thread start(int port, int numPlayers) throws IOException;
}
