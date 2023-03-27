package networking.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

//TODO should find a better name for this class
public class MessageSenderHandler {

    private DataOutputStream output;
    private ConcurrentLinkedQueue<String> data;
    public MessageSenderHandler(DataOutputStream output, ConcurrentLinkedQueue<String> data){
        this.output = output;
        this.data = data;
    }

    public int sendData() throws IOException {
        int count = 0;
        for(String message : this.data){
            this.output.writeChars(message);
            count++;
        }
        this.output.flush();

        return count;
    }
}
