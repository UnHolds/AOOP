package networking;

import networking.client.Client;
import networking.client.IClient;
import networking.server.IServer;
import networking.server.IServerClient;
import networking.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ServerTest {

    public static int SERVER_PORT = 18899;
    public static String LOCALHOST = "127.0.0.1";
    public boolean exceptionThrownInThread = false;
    public static ConcurrentLinkedQueue<Throwable> exceptions;

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

    @After
    public void checkIfThreadHasThrownException() throws InterruptedException {
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
    @DisplayName("Just start the server and close the server when the server waits for clients")
    public void testStartServerAndStopServerWhenServerWaitsForClients() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        Thread.sleep(100);
        server.stop();
        Thread.sleep(100);
    }

    @Test
    public void testStartServerAndStopServerWhenServerAcceptsOneClientAndThenWaitsForOtherClients() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        new Socket(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.stop();
        Thread.sleep(100);
    }

    @Test
    public void testStartServerAndConnectClientAndStartGameAndStopServer() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        new Socket(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        server.stop();
        Thread.sleep(100);
    }

    @Test
    public void testSendMessageToServer() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        List<IServerClient> clients = server.getClients();
        ((Server)server).skipAllClientHandling = true;
        client.sendMessage(new Message(client.getId(), client.getName(), 69));
        Thread.sleep(100);
        assertEquals(1, clients.size());
        List<IMessage> messages = clients.get(0).getMessages();
        assertEquals(1, messages.size());
        IMessage message = messages.get(0);
        assertEquals(MessageType.GAME_TICK_UPDATE, message.getMessageType());
        assertEquals(69, message.getCurrentGameTick());
    }

    @Test
    public void testSendMessageToClients() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        client.getMessages();
        server.sendToAllClients(new Message(client.getId(), client.getName(), 420));
        Thread.sleep(100);
        List<IMessage> messages = client.getMessages();
        assertEquals(1, messages.size());
        IMessage message = messages.get(0);
        assertEquals(MessageType.GAME_TICK_UPDATE, message.getMessageType());
        assertEquals(420, message.getCurrentGameTick());

    }

    @Test
    public void testSendMessageToServerAndCheckIfOtherClientGetsMessage() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        IClient clientSender = new Client("Sender");
        clientSender.connect(LOCALHOST, SERVER_PORT);
        IClient clientReceiver = new Client("Receiver");
        clientReceiver.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        clientSender.getMessages();
        clientReceiver.getMessages();
        clientSender.sendMessage(new Message(clientSender.getId(), clientSender.getName(), 666));
        Thread.sleep(100);

        List<IMessage> messagesReceiver = clientReceiver.getMessages();
        assertEquals(1, messagesReceiver.size());
        IMessage messageReceiver = messagesReceiver.get(0);
        assertEquals(MessageType.GAME_TICK_UPDATE, messageReceiver.getMessageType());
        assertEquals(666, messageReceiver.getCurrentGameTick());
        assertEquals(clientSender.getId(), messageReceiver.getSenderId());
        assertEquals("Sender", messageReceiver.getSenderName());

        List<IMessage> messagesSender = clientSender.getMessages();
        assertEquals(1, messagesSender.size());
        IMessage messageSender = messagesSender.get(0);
        assertEquals(MessageType.GAME_TICK_UPDATE, messageSender.getMessageType());
        assertEquals(666, messageSender.getCurrentGameTick());
        assertEquals(clientSender.getId(), messageSender.getSenderId());
        assertEquals("Sender", messageSender.getSenderName());
    }

    @Test
    public void testIfGameTickUpdateIsSentToClientsOnStart() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        List<IMessage> messages = client.getMessages();
        assertEquals(1, messages.size());
        IMessage message = messages.get(0);
        assertEquals(MessageType.GAME_TICK_UPDATE, message.getMessageType());
        assertEquals("server", message.getSenderName());
        assertEquals("server", message.getSenderId());
        assertEquals(System.currentTimeMillis() / 10000, message.getCurrentGameTick() / 10000);

    }

    @Test
    public void testServerStartAndStopIfClientIsConnected() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        server.stop();

    }
}
