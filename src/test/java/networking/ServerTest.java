package networking;

import networking.client.Client;
import networking.client.IClient;
import networking.server.IServer;
import networking.server.IServerClient;
import networking.server.Server;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertEquals;

public class ServerTest {

    public static int SERVER_PORT = 18899;
    public static String LOCALHOST = "127.0.0.1";

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
        client.sendMessage(new Message(client,69));
        Thread.sleep(100);
        assertEquals(clients.size(), 1);
        List<IMessage> messages = clients.get(0).getMessages();
        assertEquals(messages.size(), 1);
        IMessage message = messages.get(0);
        assertEquals(message.getMessageType(), MessageType.GAME_TICK_UPDATE);
        assertEquals(message.getCurrentGameTick(), 69);
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
        server.sendToAllClients(new Message(client,420));
        Thread.sleep(100);
        List<IMessage> messages = client.getMessages();
        assertEquals(messages.size(), 1);
        IMessage message = messages.get(0);
        assertEquals(message.getMessageType(), MessageType.GAME_TICK_UPDATE);
        assertEquals(message.getCurrentGameTick(), 420);

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
        clientSender.sendMessage(new Message(clientSender, 666));
        Thread.sleep(100);

        List<IMessage> messagesReceiver = clientReceiver.getMessages();
        assertEquals(messagesReceiver.size(), 1);
        IMessage messageReceiver = messagesReceiver.get(0);
        assertEquals(messageReceiver.getMessageType(), MessageType.GAME_TICK_UPDATE);
        assertEquals(messageReceiver.getCurrentGameTick(), 666);
        assertEquals(messageReceiver.getSenderId(), clientSender.getId());
        assertEquals(messageReceiver.getSenderName(), "Sender");

        List<IMessage> messagesSender = clientSender.getMessages();
        assertEquals(messagesSender.size(), 1);
        IMessage messageSender = messagesSender.get(0);
        assertEquals(messageSender.getMessageType(), MessageType.GAME_TICK_UPDATE);
        assertEquals(messageSender.getCurrentGameTick(), 666);
        assertEquals(messageSender.getSenderId(), clientSender.getId());
        assertEquals(messageSender.getSenderName(), "Sender");
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
        assertEquals(messages.size(), 1);
        IMessage message = messages.get(0);
        assertEquals(message.getMessageType(), MessageType.GAME_TICK_UPDATE);
        assertEquals(message.getSenderName(), "server");
        assertEquals(message.getSenderId(), "server");
        assertEquals(message.getCurrentGameTick() / 10000, System.currentTimeMillis() / 10000);

    }
}
