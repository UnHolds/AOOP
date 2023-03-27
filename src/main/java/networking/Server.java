package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server implements IServer, Runnable{

    private ServerSocket socket;
    private List<IServerClient> clients = new ArrayList<>();
    private int numPlayers;
    private static Logger log = LogManager.getLogger(Server.class);

    private void waitForClients() {
        for(int i = 0; i < this.numPlayers; i++) {
            try {
                Socket client = this.socket.accept();
                IServerClient serverClient = new ServerClient(client);
                this.clients.add(serverClient);
                serverClient.dispatch();
                this.log.info("Client num " + (i+1) +" of " + this.numPlayers + " has connected with address " + client.getInetAddress().getHostAddress());
            }catch (IOException e){
                this.log.debug("Could not accept client", e);
                i--;
            }
        }
        this.log.info("All clients have connected to the server");
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
        waitForClients();
    }
}
