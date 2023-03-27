package networking.server;

import networking.IMessage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

//TODO should find a better name for this class
public class MessageSenderHandler {

    private DataOutputStream output;
    private ConcurrentLinkedQueue<IMessage> data;
    public MessageSenderHandler(DataOutputStream output, ConcurrentLinkedQueue<IMessage> data){
        this.output = output;
        this.data = data;
    }

    public int sendData() throws IOException {
        int count = 0;
        for(IMessage message : this.data){
            this.output.write(message.toBytes());
            count++;
        }
        this.output.flush();

        return count;
    }
}
