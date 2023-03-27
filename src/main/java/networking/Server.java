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
    private boolean acceptClients;
    private static Logger log = LogManager.getLogger(Server.class);
    private boolean stopped = false;

    private void waitForClients() {
        this.log.info("Waiting for clients");
        while(this.acceptClients && this.stopped == false) {
            try {
                Socket client = this.socket.accept();
                IServerClient serverClient = new ServerClient(client);
                this.clients.add(serverClient);
                serverClient.dispatch();
                this.log.info("Client has connected with address " + client.getInetAddress().getHostAddress());
            }catch (IOException e){
                if(this.socket.isClosed() && this.stopped){
                    return;
                }else if(this.socket.isClosed() && this.acceptClients == false){
                    break;
                }
                else{
                    this.log.debug("Could not accept client", e);
                }
            }
        }
        this.log.info("No longer accepting clients");
    }

    @Override
    public Thread start(int port) throws IOException {
        this.log.info("starting server on port: " + port);
        this.socket = new ServerSocket(port);
        this.acceptClients = true;
        Thread t = new Thread(this);
        t.start();
        return t;
    }

    @Override
    public void startGame() {
        this.acceptClients = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            this.log.error("Could not close serverSocket", e);
        }
    }

    @Override
    public void stop() throws IOException {
        this.log.info("Stopping server");
        this.stopped = true;
        this.acceptClients = false;

        if(this.socket.isClosed() == false){
            this.socket.close();
        }
        for(IServerClient serverClient : this.clients){
            serverClient.stop();
        }
        //TODO maybe do more stop
    }

    private void mainServerLoop(){
        this.log.info("Starting main server loop");
    }



    @Override
    public void run() {
        waitForClients();
        mainServerLoop();
        this.log.info("Server thread stopped");
    }
}
