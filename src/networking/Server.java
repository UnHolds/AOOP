package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements IServer, Runnable{

    private ServerSocket socket;
    private List<ServerClient> clients = new ArrayList<>();
    private int numPlayers;

    private void waitForClients() throws IOException {
        Socket client = this.socket.accept();
        this.clients.add(new ServerClient(client));
    }

    @Override
    public Thread start(int port, int numPlayers) throws IOException {
        this.numPlayers = numPlayers;
        this.socket = new ServerSocket(port);
        Thread t = new Thread(this);
        t.start();
        return t;
    }

    @Override
    public void run() {

    }
}
