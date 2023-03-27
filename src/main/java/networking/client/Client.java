package networking.client;

import networking.IMessage;
import networking.Message;
import networking.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Client implements IClient, Runnable{

    private DataInputStream input;
    private  DataOutputStream output;
    private Socket server;
    private static final int DATA_SIZE = 512;
    private boolean stop = false;
    private List<IMessage> messages = new ArrayList<>();
    private static Logger log = LogManager.getLogger(Server.class);
    @Override
    public void connect(String address, int port) throws IOException {
        this.server = new Socket(address, port);
        this.input = new DataInputStream(this.server.getInputStream());
        this.output = new DataOutputStream(this.server.getOutputStream());
    }

    @Override
    public void close(){
        this.stop = true;
        try {
            this.server.close();
        } catch (IOException e) {
            log.debug("Could not close client socket", e);
        }
    }

    private void mainClientLoop(){
        while(this.stop == false && this.server.isClosed() == false) {
            byte[] data = new byte[DATA_SIZE];
            try {
                input.readFully(data);

                synchronized (this) {
                    messages.add(new Message(data));
                }
            } catch (IOException e) {
                log.error("Could not read from server", e);
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
    public void run() {
        mainClientLoop();
        log.debug("Client thread exited");
    }
}
