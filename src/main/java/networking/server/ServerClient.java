package networking.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerClient implements IServerClient, Runnable{


    private Socket client;
    private DataInputStream input;
    private  DataOutputStream output;
    private Thread thread;
    private boolean stopped;

    private MessageSender sender;

    private ConcurrentLinkedQueue<String> receivedData = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<String> sendData = new ConcurrentLinkedQueue<>();

    private static Logger log = LogManager.getLogger(ServerClient.class);

    public ServerClient(Socket client, MessageSender sender) throws IOException {
        this.client = client;
        this.input = new DataInputStream(client.getInputStream());
        this.output = new DataOutputStream(client.getOutputStream());
        this.sender = sender;
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
        try {
            this.client.close();
        } catch (IOException e) {
            this.log.error("Could not close socket in ServerClient: " + this.client.getInetAddress().getHostAddress(), e);
        }
    }

    private void mainServerClientLoop(){
        while(this.stopped == false){
        }
    }

    @Override
    public void run() {
        this.log.debug("Attach message sender to sever client: " + this.client.getInetAddress().getHostAddress());
        this.sender.addSendWatch(new MessageSenderHandler(this.output, this.sendData));
        mainServerClientLoop();
        this.log.info("Client: " + this.client.getInetAddress().getHostAddress() + " thread stopped");
    }
}
