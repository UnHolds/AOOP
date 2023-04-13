package networking.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

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

    public boolean skipAllClientHandling = false;

    public boolean forwardEverything = false;

    private MessageFactory messageFactory = new MessageFactory(this);

    private boolean newMessages = false;

    public long gameTick = 0;

    private ConcurrentLinkedQueue<IMessage> receivedMessages = new ConcurrentLinkedQueue<>();


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
    public List<IMessage> getAllMessages() {
        List<IMessage> messages = this.receivedMessages.stream().toList();
        this.receivedMessages.clear();
        return messages;
    }

    @Override
    public Thread getThread() {
        return this.thread;
    }

    private List<IMessage> filterClientMessages(List<IMessage> messages){
        //TODO filter duplicated messages
        return messages;
    }


    private void mainServerLoop(){
        log.info("Starting main server loop");

        this.gameTick = System.currentTimeMillis();
        sendToAllClients(messageFactory.createGameTickUpdateMessage(this.gameTick));

        while(this.stop == false){
            try {
                synchronized (this) {
                    if(this.newMessages == false) {
                        this.wait();
                    }
                    this.newMessages = false;
                }
            } catch (InterruptedException e) {
                log.error("Interrupted while wait");
            }

            if(this.skipAllClientHandling){
                continue;
            }

            List<IMessage> messages = new ArrayList<>();

            for(IServerClient client : this.clients){

                //remove disconnected clients
                if(client.isDisconnected()){
                    client.stop();
                    this.clients.remove(client);
                }
                messages.addAll(filterClientMessages(client.getMessages()));
            }

            log.debug("Received " + messages.size() + " messages");

            if(this.forwardEverything){
                for(IMessage message : messages){
                    sendToAllClients(message);
                }
                continue;
            }


            this.receivedMessages.addAll(messages);
            /*
            messages = handleMessages(messages);

            for(IMessage message : messages){
                sendToAllClients(message);
            }
            */
        }
    }



    @Override
    public void run() {
        waitForClients();
        mainServerLoop();
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
