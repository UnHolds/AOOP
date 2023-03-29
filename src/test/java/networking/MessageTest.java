package networking;

import networking.client.Client;
import networking.client.IClient;
import networking.server.IServer;
import networking.server.Server;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.List;


public class MessageTest {

    public static int SERVER_PORT = 18899;
    public static String LOCALHOST = "127.0.0.1";
    private IServer server;
    private IClient sender;
    private IClient receiver;

    private IMessageFactory messageFactorySender;

    @BeforeEach
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

    @AfterEach
    public void serverTeardown() throws IOException {
        this.server.stop();
        this.sender.stop();
        this.receiver.stop();
    }

    @Test
    public void testIfCatPositionMessageReturnsRightXCoordinate(){

    }
}
