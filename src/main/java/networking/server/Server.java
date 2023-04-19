package networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import networking.IMessage;
import networking.MessageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server implements IServer, Runnable{

    private ServerSocket socket;
    private ConcurrentLinkedQueue<IServerClient> clients = new ConcurrentLinkedQueue<>();
    private boolean acceptClients;
    private static Logger log = LogManager.getLogger(Server.class);
    private boolean stop = false;

    private Thread thread;

    private MessageFactory messageFactory = new MessageFactory(this);

    private boolean newMessages = false;

    public long gameTick = 0;

    private BlockingQueue<IMessage> messageQueue = new LinkedBlockingQueue<>();


    @Override
    public void sendUpdateConnectedClientsMessage(){

        List<IServerClient> clients;
        synchronized (this) {
            clients = this.clients.stream().filter(c -> c.getId() != null).toList();
        }
        sendToAllClients(messageFactory.createConnectedClientsUpdateMessage(clients));
    }

    private void waitForClients() {
        log.info("Waiting for clients");
        while(this.acceptClients && this.stop == false) {
            try {
                Socket client = this.socket.accept();
                IServerClient serverClient = new ServerClient(client, this);
                synchronized (this) {
                    this.clients.add(serverClient);
                }
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
    public void startGame(List<IMessage> startMessages) {
        this.acceptClients = false;
        try {
            this.socket.close();
        } catch (IOException e) {
            log.error("Could not close serverSocket", e);
        }

        for(IMessage message : startMessages){
            sendToAllClients(message);
        }
    }

    @Override
    public void notifyNewMessages() {
        synchronized (this) {
            this.notify();
            this.newMessages = true;
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
        return this.clients.stream().toList();
    }

    @Override
    public void sendToAllClients(IMessage message){

        if(this.stop){
            log.info("Didn't send message because server has stopped");
            return;
        }

        for(IServerClient client : this.clients){
            try {
                client.sendMessage(message);
            } catch (IOException e) {
                log.error("Could not send message to client: " + client.getAddress(), e);
            }
        }
    }

    @Override
    public BlockingQueue<IMessage> getMessageQueue() {
        return messageQueue;
    }

    @Override
    public Thread getThread() {
        return this.thread;
    }

    private List<IMessage> filterClientMessages(List<IMessage> messages){
        //TODO filter duplicated messages
        return messages;
    }


    @Override
    public void run() {
        waitForClients();
        for(IServerClient client : clients) {
            try {
                client.getThread().join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("Server thread stopped");
    }

    @Override
    public String getId() {
        return "server";
    }

    @Override
    public String getName() {
        return "server";
    }
}
