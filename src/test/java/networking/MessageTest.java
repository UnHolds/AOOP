package networking;

import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.impl.Field;
import game.core.models.impl.Mouse;
import game.core.models.impl.Player;
import game.core.models.impl.Subway;
import networking.client.Client;
import networking.client.IClient;
import networking.server.IServer;
import networking.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.*;


public class MessageTest {

    public static int SERVER_PORT = 18899;
    public static String LOCALHOST = "127.0.0.1";
    private IServer server;
    private IClient client1;

    private IClient client2;

    private IMessageFactory messageFactory;

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
        this.client1 = new Client("Client 1");
        this.client2 = new Client("Client 2");
        this.messageFactory = new MessageFactory(this.client1);
        this.client1.connect(LOCALHOST, SERVER_PORT);
        this.client2.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        this.server.startGame(new ArrayList<>()); //TODO change
        Thread.sleep(100);

        while(server.getThread().getState() != Thread.State.WAITING){
            Thread.sleep(100);
        }

        this.client2.getMessageQueue().clear();
        this.client1.getMessageQueue().clear();

    }

    @After
    public void serverTeardown() throws IOException, InterruptedException {
        Thread.sleep(100);
        this.server.stop();
        this.client1.stop();
        this.client2.stop();

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
    public void testGameFieldUpdateMessage() throws InterruptedException {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(3, 4);
        Position pos3 = new Position(5, 6);
        Position pos4 = new Position(7, 8);
        Position pos5 = new Position(0,0);

        IPlayer player1 = new Player(this.client1.getId(), -1, "Player 1", pos1, "cat1.png");
        IPlayer player2 = new Player(this.client2.getId(), -1, "Player 2", pos2, "cat2.png");

        List<Position> sub = new ArrayList<>();
        sub.add(pos5);
        Subway subway = new Subway(sub);

        Field field = null;
        IMouse mouse1 = new Mouse("ID_MOUSE_1", pos3, "mouse.png", -1);
        IMouse mouse2 = new Mouse("ID_MOUSE_2", pos4, "mouse.png", -1);

        List<IPlayer> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        List<IMouse> mice = new ArrayList<>();
        mice.add(mouse1);
        mice.add(mouse2);

        IMessage gameFieldUpdateMessage = this.messageFactory.createGameFieldUpdateMessage(420, players, mice);

        this.server.sendToAllClients(gameFieldUpdateMessage);

        Thread.sleep(100);

        List<IMessage> messages = this.client1.getMessageQueue().stream().toList();

        assertEquals(1, messages.size());
        IMessage message = messages.get(0);
        assertEquals(MessageType.GAME_FIELD_UPDATE, message.getMessageType());

        Map<String, Position> playerPos = message.getPlayersPositions();
        Map<String, Position> micePos = message.getMicePositions();

        assertEquals(2, playerPos.size());
        assertEquals(2, micePos.size());

        assertTrue(playerPos.keySet().containsAll(Arrays.asList(this.client1.getId(), this.client2.getId())));
        assertTrue(micePos.keySet().containsAll(Arrays.asList("ID_MOUSE_1", "ID_MOUSE_2")));

        assertEquals(pos1, playerPos.get(this.client1.getId()));
        assertEquals(pos2, playerPos.get(this.client2.getId()));

        assertEquals(pos3, micePos.get("ID_MOUSE_1"));
        assertEquals(pos4, micePos.get("ID_MOUSE_2"));
    }

}
