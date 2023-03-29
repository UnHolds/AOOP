package networking;

import networking.client.Client;
import networking.client.IClient;
import networking.server.IServer;
import networking.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;


public class MessageTest {

    public static int SERVER_PORT = 18899;
    public static String LOCALHOST = "127.0.0.1";
    private IServer server;
    private IClient sender;
    private IClient receiver;

    private IMessageFactory messageFactorySender;

    public boolean exceptionThrownInThread = false;
    public ConcurrentLinkedQueue<Throwable> exceptions;

    @Before
    public void setExceptionHandler(){

        exceptions = new ConcurrentLinkedQueue<>();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                exceptionThrownInThread = true;
                exceptions.add(e);
            }
        });
    }

    @Before
    public void serverSetup() throws IOException, InterruptedException {

        this.server = new Server();
        this.server.start(SERVER_PORT);
        Thread.sleep(100);
        this.sender = new Client("Sender");
        this.receiver = new Client("Receiver");
        this.messageFactorySender = new MessageFactory(this.sender);
        this.sender.connect(LOCALHOST, SERVER_PORT);
        this.receiver.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        this.server.startGame();
        Thread.sleep(100);
        this.receiver.getMessages();
        this.sender.getMessages();

    }

    @After
    public void serverTeardown() throws IOException, InterruptedException {
        Thread.sleep(100);
        this.server.stop();
        this.sender.stop();
        this.receiver.stop();

        Thread.sleep(100);
        if(exceptionThrownInThread == true){
            for(Throwable e : exceptions){
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        assertFalse(exceptionThrownInThread);

    }

    @Test
    public void testIfCatPositionMessageReturnsRightXCoordinate() throws InterruptedException {
        ((Server)this.server).forwardEverythingWithoutHandling = true;
        this.sender.sendMessage(this.messageFactorySender.createCatPositionMessage(System.currentTimeMillis(), 1, 2, 3));
        Thread.sleep(100);
        List<IMessage> messages = this.receiver.getMessages();
        assertEquals(1, messages.size());
        IMessage message = messages.get(0);
        assertEquals(1, message.getCatPositionX());
        assertEquals(2, message.getCatPositionY());
        assertEquals(3, message.getCatPositionZ());
        assertEquals(3, message.getCatPosition().length);
        assertTrue(Arrays.equals(new int[] { 1, 2, 3}, message.getCatPosition()));
    }
}
