package networking.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSender implements Runnable{

    private Thread thread;
    private boolean stop = false;
    private Logger log = LogManager.getLogger(MessageSender.class);

    private ConcurrentLinkedQueue<MessageSenderHandler> handlers;

    public MessageSender(){
        this.thread = new Thread(this);
        this.thread.start();
    }



    public void addSendWatch(MessageSenderHandler handler){
        this.handlers.add(handler);
    }

    public void sendDataNow(){
        this.thread.interrupt();
    }

    public void stop(){
        this.stop = true;
    }

    @Override
    public void run() {
        while(this.stop == false) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignored
            }
            int sendCount = 0;
            for(MessageSenderHandler handler : this.handlers){
                try {
                    sendCount += handler.sendData();
                } catch (IOException e) {
                    this.log.error("Could not send data for handler", e);
                }
            }

            if(sendCount > 0) {
                this.log.debug("Send out " + sendCount + " messages");
            }
        }
        this.log.info("Message Sender thread exited");
    }
}
