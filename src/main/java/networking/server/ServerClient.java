package networking.server;

import networking.IMessage;
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
    private boolean stop;
    private static final int DATA_SIZE = 512;

    private MessageSender sender;

    private IServer server;

    private ConcurrentLinkedQueue<IMessage> messagesToSent = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<IMessage> messagesReceived = new ConcurrentLinkedQueue<>();
    private static Logger log = LogManager.getLogger(ServerClient.class);

    public ServerClient(Socket client, MessageSender sender, IServer server) throws IOException {
        this.client = client;
        this.input = new DataInputStream(client.getInputStream());
        this.output = new DataOutputStream(client.getOutputStream());
        this.sender = sender;
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
    public void sendMessage(IMessage message) {
        this.messagesToSent.add(message);
    }

    @Override
    public void sendMessageNow(IMessage message) {
        this.sendMessage(message);
        this.sender.sendDataNow();
    }

    private IMessage readMessage(){
        byte[] data = new byte[DATA_SIZE];
        try {
            this.input.read(data);
        } catch (IOException e) {
            this.log.error("Could not read from data input stream, client: " + this.client.getInetAddress().getHostAddress());
        }

        //TODO ADD MESSAGE PARSER TO GET IMESSAGE

        return null;
    }

    private void mainServerClientLoop(){
        while(this.stop == false){
            this.messagesReceived.add(readMessage());
            this.server.notifyNewMessages();
        }
    }

    @Override
    public void run() {
        this.log.debug("Attach message sender to sever client: " + this.client.getInetAddress().getHostAddress());
        this.sender.addSendWatch(new MessageSenderHandler(this.output, this.messagesToSent));
        mainServerClientLoop();
        this.log.info("Client: " + this.client.getInetAddress().getHostAddress() + " thread stopped");
    }
}
