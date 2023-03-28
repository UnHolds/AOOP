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

    @Test
    @DisplayName("Just start the server and close the server when the server waits for clients")
    public void testStartServerAndStopServerWhenServerWaitsForClients() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(18899);
        Thread.sleep(100);
        server.stop();
        Thread.sleep(100);
    }

    @Test
    public void testStartServerAndStopServerWhenServerAcceptsOneClientAndThenWaitsForOtherClients() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(18899);
        new Socket("127.0.0.1", 18899);
        Thread.sleep(100);
        server.stop();
        Thread.sleep(100);
    }

    @Test
    public void testStartServerAndConnectClientAndStartGameAndStopServer() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(18899);
        new Socket("127.0.0.1", 18899);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        server.stop();
        Thread.sleep(100);
    }

    @Test
    public void testSendMessageToServer() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(18899);
        IClient client = new Client("Client");
        client.connect("127.02.0.1", 18899);
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
        server.start(18899);
        IClient client = new Client("Client");
        client.connect("127.02.0.1", 18899);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
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
        server.start(18899);
        IClient clientSender = new Client("Sender");
        clientSender.connect("127.02.0.1", 18899);
        IClient clientReceiver = new Client("Receiver");
        clientReceiver.connect("127.02.0.1", 18899);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
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
}
