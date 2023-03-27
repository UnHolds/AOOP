package networking;

import java.io.IOException;

public interface IServer extends Runnable{

    /**
     * Starts the server on the given port and accepts infinitely many clients,
     * until the startGame() method is called
     * This function will not hold until all clients are connected.
     * Rather it creates a new thread and dispatches it,this thread will then be
     * returned by the function.
     * @param port the port on which the server should listen on
     * @return the created and dispatched thread of the server
     * @throws IOException
     */
    Thread start(int port) throws IOException;

    void startGame();

    void stop() throws IOException;
}
