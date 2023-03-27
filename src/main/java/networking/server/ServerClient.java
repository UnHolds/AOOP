package networking.server;

import networking.IMessage;
import networking.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerClient implements IServerClient, Runnable{


    private Socket client;
    private BufferedReader input;
    private  PrintWriter output;
    private Thread thread;
    private boolean stop;


    private IServer server;

    private ConcurrentLinkedQueue<IMessage> messagesReceived = new ConcurrentLinkedQueue<>();
    private static Logger log = LogManager.getLogger(ServerClient.class);

    public ServerClient(Socket client, IServer server) throws IOException {
        this.client = client;
        this.input = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        this.output = new PrintWriter(this.client.getOutputStream(), true);
        this.server = server;
    }

    public void dispatch(){
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public Thread getThread() {
        return this.thread;
    }

    @Override
    public void stop() {
        this.stop = true;
        try {
            this.client.close();
        } catch (IOException e) {
            this.log.error("Could not close socket in ServerClient: " + this.client.getInetAddress().getHostAddress(), e);
        }
    }

    @Override
    public ConcurrentLinkedQueue<IMessage> getMessages() {
        return this.messagesReceived;
    }

    @Override
    public void sendMessage(IMessage message) throws IOException {
        this.output.println(message.toBase64String());
    }

    @Override
    public String getAddress() {
        return this.client.getInetAddress().getHostAddress();
    }


    private void fetchMessages(){
        try {
            this.messagesReceived.add(new Message(this.input.readLine()));
            this.server.notifyNewMessages();
        } catch (IOException e) {
            this.log.error("Could not read from data input stream, client: " + this.client.getInetAddress().getHostAddress());
        }
    }

    private void mainServerClientLoop(){
        while(this.stop == false){
            fetchMessages();
        }
    }

    @Override
    public void run() {
        this.log.debug("Executing main server client loop for client: " + this.client.getInetAddress().getHostAddress());
        mainServerClientLoop();
        this.log.info("Client: " + this.client.getInetAddress().getHostAddress() + " thread stopped");
    }
}
