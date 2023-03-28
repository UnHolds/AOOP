package networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import networking.IMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server implements IServer, Runnable{

    private ServerSocket socket;
    private List<IServerClient> clients = new ArrayList<>();
    private boolean acceptClients;
    private static Logger log = LogManager.getLogger(Server.class);
    private boolean stop = false;

    private Thread thread;

    public boolean skipAllClientHandling = false;


    private void waitForClients() {
        log.info("Waiting for clients");
        while(this.acceptClients && this.stop == false) {
            try {
                Socket client = this.socket.accept();
                IServerClient serverClient = new ServerClient(client, this);
                this.clients.add(serverClient);
                serverClient.dispatch();
                log.info("Client has connected with address " + client.getInetAddress().getHostAddress());
            }catch (IOException e){
                if(this.socket.isClosed() && this.stop){
                    return;
                }else if(this.socket.isClosed() && this.acceptClients == false){
                    break;
                }
                else{
                    log.debug("Could not accept client", e);
                }
            }
        }
        log.info("No longer accepting clients");
    }

    @Override
    public void start(int port) throws IOException {
        log.info("starting server on port: " + port);
        this.socket = new ServerSocket(port);
        this.acceptClients = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void startGame() {
        this.acceptClients = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            log.error("Could not close serverSocket", e);
        }
    }

    @Override
    public void notifyNewMessages() {
        synchronized (this) {
            this.notify();
        }
    }

    @Override
    public void stop() throws IOException {
        log.info("Stopping server");
        this.stop = true;
        this.acceptClients = false;

        if(this.socket.isClosed() == false){
            this.socket.close();
        }
        for(IServerClient serverClient : this.clients){
            serverClient.stop();
        }
        //TODO maybe do more stop
    }

    @Override
    public List<IServerClient> getClients() {
        return this.clients;
    }

    @Override
    public void sendToAllClients(IMessage message){
        for(IServerClient client : this.clients){
            try {
                client.sendMessage(message);
            } catch (IOException e) {
                log.error("Could not send message to client: " + client.getAddress(), e);
            }
        }
    }

    @Override
    public Thread getThread() {
        return this.thread;
    }

    private void mainServerLoop(){
        log.info("Starting main server loop");

        while(this.stop == false){
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                //ignored
            }

            if(this.skipAllClientHandling){
                continue;
            }

            for(IServerClient client : this.clients){

                //remove disconnected clients
                if(client.isDisconnected()){
                    client.stop();
                    this.clients.remove(client);
                }

                for(IMessage message : client.getMessages()) {
                    //TODO HANDLE MESSAGE
                }

            }
            //TODO HANDLE NEW MESSAGES
        }
    }



    @Override
    public void run() {
        waitForClients();
        mainServerLoop();
        log.info("Server thread stopped");
    }
}
