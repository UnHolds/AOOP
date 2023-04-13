package networking;

import networking.client.Client;
import networking.client.IClient;
import networking.server.IServer;
import networking.server.IServerClient;
import networking.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ServerTest {

    public static int SERVER_PORT = 18899;
    public static String LOCALHOST = "127.0.0.1";
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
        ((Server)server).forwardEverything = true;
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
        client.getMessages();
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

    @Test
    public void testServerStartAndDisconnectClient() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        client.stop();
    }

    @Test
    public void testServerStartAndDisconnectClientAndOtherClientStillConnectedSeverSendMessageToAllClients() throws IOException, InterruptedException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        IClient clientDisconnect = new Client("ClientDisconnect");
        IClient clientConnected = new Client("ClientConnected");
        clientDisconnect.connect(LOCALHOST, SERVER_PORT);
        clientConnected.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        clientConnected.getMessages();
        clientDisconnect.getMessages();
        Thread.sleep(100);
        clientDisconnect.stop();
        Thread.sleep(100);
        server.sendToAllClients(new Message("server", "server", 123));
        Thread.sleep(100);

        List<IMessage> messagesClientConnected = clientConnected.getMessages();
        List<IMessage> messagesClientDisconnected = clientConnected.getMessages();
        assertEquals(0, messagesClientDisconnected.size());
        assertEquals(1, messagesClientConnected.size());
        assertEquals(123, messagesClientConnected.get(0).getCurrentGameTick());

    }

    @Test
    public void testSendMessageToServerIfServerIsStopped() throws InterruptedException, IOException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        server.stop();
        Thread.sleep(100);
        client.sendMessage(new Message(client.getId(), client.getName(), 555));
    }

    @Test
    public void testSendMessageFromServerIfServerHasStopped() throws InterruptedException, IOException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        server.stop();
        Thread.sleep(100);
        server.sendToAllClients(new Message(client.getId(), client.getName(), 555));
    }

    @Test
    public void testSendMessageToServerIfClientHasStopped() throws InterruptedException, IOException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        Thread.sleep(100);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        server.startGame();
        Thread.sleep(100);
        client.stop();
        Thread.sleep(100);
        server.sendToAllClients(new Message(client.getId(), client.getName(), 555));
    }

    @Test
    public void testLotOfClientsSendMessages() throws IOException, InterruptedException {

        int numClients = 50;
        int numMessages = 20;

        IServer server = new Server();
        ((Server)server).forwardEverything = true;
        server.start(SERVER_PORT);
        Thread.sleep(100);

        List<IClient> clients = new ArrayList<>();

        for(int i =0; i<numClients; i++){
            Client client = new Client("Client" + i);
            clients.add(client);
            client.connect(LOCALHOST, SERVER_PORT);
        }
        Thread.sleep(500);
        server.startGame();
        Thread.sleep(100);

        for(IClient client : clients){
            client.getMessages();
        }

        Thread.sleep(100);

        List<Thread> threads = new ArrayList<>();

        for(int j = 0; j < clients.size(); j++){
            IClient client = clients.get(j);
            int finalJ = j;
            Thread thread = new Thread(() -> {
                for(int i = 0; i< numMessages; i++){
                    client.sendMessage(new Message(client.getId(), client.getName(),  10001 + i + finalJ * numMessages));
                }
            });
            threads.add(thread);
        }

        for(int i = 0; i < threads.size(); i++){
            if(i % 2 == 0){
                threads.get(i).start();
            }
        }

        for(int i = 0; i < threads.size(); i++){
            if(i % 2 == 0){
                threads.get(i).join();
            }
        }

        Thread.sleep(500);

        for(int i = 0; i < threads.size(); i++){
            if(i % 2 == 1){
                threads.get(i).start();
            }
        }

        for(int i = 0; i < threads.size(); i++){
            if(i % 2 == 1){
                threads.get(i).join();
            }
        }

        //wait for thread to handle all messages
        while(server.getThread().getState() != Thread.State.WAITING){
            Thread.sleep(100);
        }

        //helper list
        ArrayList<Long> numbers = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            numbers.add(10001l + i);
        }

        for(IClient client : clients){
            List<IMessage> messages =  client.getMessages();
            List<Long> gameTicks = messages.stream().map(m -> m.getCurrentGameTick()).collect(Collectors.toList());
            List<Long> missingGameTicks = numbers.stream().filter(n -> gameTicks.contains(n) == false).collect(Collectors.toList());
            assertEquals(numClients*numMessages, messages.size());
        }
    }

    @Test
    public void testIfServerReceivesClientConnectMessageAndSetsIdAndName() throws InterruptedException, IOException {
        IServer server = new Server();
        ((Server)server).skipAllClientHandling = true;
        server.start(SERVER_PORT);
        Thread.sleep(100);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);

        assertEquals(0, server.getClients().get(0).getMessages().size());
        assertEquals(client.getId(), server.getClients().get(0).getId());
        assertEquals(client.getName(), server.getClients().get(0).getName());

        server.startGame();

    }

    @Test
    public void testIfClientReceiveClientsConnectedUpdateMessage() throws InterruptedException, IOException {
        IServer server = new Server();
        server.start(SERVER_PORT);
        Thread.sleep(100);
        IClient client = new Client("Client");
        client.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);

        IMessage message = client.getMessages().get(0);

        assertEquals(MessageType.CONNECTED_CLIENTS_UPDATE, message.getMessageType());
        assertEquals(server.getId(), message.getSenderId());
        assertEquals(server.getName(), message.getSenderName());
        HashMap<String, String> clients = message.getClientIdAndName();
        assertEquals(1, clients.size());
        assertEquals(client.getId(), clients.keySet().toArray()[0]);
        assertEquals(client.getName(), clients.values().toArray()[0]);

        server.startGame();


    }
}
