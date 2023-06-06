package networking.client;

import networking.IMessage;
import networking.IMessageFactory;
import networking.Message;
import networking.MessageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Client implements IClient, Runnable{

    private BufferedReader input;
    private PrintWriter output;
    private Socket server;
    private boolean stop = false;
    private List<IMessage> messages = new ArrayList<>();
    private static Logger log = LogManager.getLogger(Client.class);

    private String id = UUID.randomUUID().toString();

    private String name;

    private Thread thread;

    private IMessageFactory messageFactory = new MessageFactory(this);

    public Client(String name){
        this.name = name;
    }
    @Override
    public void connect(String address, int port) throws IOException {
        this.server = new Socket(address, port);
        this.input = new BufferedReader(new InputStreamReader(this.server.getInputStream()));
        this.output = new PrintWriter(this.server.getOutputStream(), true);
        this.thread = new Thread(this);
        this.thread.start();
        sendMessage(this.messageFactory.createClientConnectMessage());
    }

    @Override
    public void stop(){
        log.info("stopping client");
        this.stop = true;
        try {
            this.server.close();
        } catch (IOException e) {
            log.debug("Could not close client socket", e);
        }
    }

    private void mainClientLoop(){
        while(this.stop == false && this.server.isClosed() == false) {
            try {
                String data = this.input.readLine();
                if(data == null){
                    stop();
                    break;
                }

                messages.add(Message.parse(data));
            } catch (IOException e) {

                if(this.server.isClosed()){
                    log.error("Server has closed socket");
                }else{
                    log.error("Could not read from server", e);
                }
            } catch (ClassNotFoundException e) {
                this.log.error("Could not convert base64 string to class", e);
            }
        }
    }

    @Override
    public List<IMessage> getMessages() {
        synchronized (this){
            List<IMessage> messages = this.messages;
            this.messages = new ArrayList<>();
            return messages;
        }
    }

    @Override
    public void sendMessage(IMessage message) {


        if(this.stop){
            log.info("Didn't send message to server because client has stopped");
            return;
        }

        try {
            log.debug("Sending message to server from client " + this.name);
            this.output.println(message.toBase64String());
        } catch (IOException e) {
            log.error("Could not send message to server", e);
        }
    }

    @Override
    public Thread getThread() {
        return this.thread;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public void run() {
        log.info("Executing main client loop");
        mainClientLoop();
        log.debug("Client thread exited");
    }
}
