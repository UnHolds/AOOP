package networking;

import game.core.handler.Direction;
import game.core.models.*;
import game.core.models.impl.*;
import game.core.models.impl.moveAlgorithms.DirectAlgorithm;
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
import java.util.stream.Collectors;

import static org.junit.Assert.*;


public class MessageTest {

    public static int SERVER_PORT = 18899;
    public static String LOCALHOST = "127.0.0.1";
    private IServer server;
    private IClient client1;

    private IClient client2;

    private IMessageFactory client1MessageFactory;
    private IMessageFactory client2MessageFactory;
    private IMessageFactory serverMessageFactory;

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
        this.client1MessageFactory = new MessageFactory(this.client1);
        this.client2MessageFactory = new MessageFactory(this.client2);
        this.serverMessageFactory = new MessageFactory(this.server);
        this.client1.connect(LOCALHOST, SERVER_PORT);
        this.client2.connect(LOCALHOST, SERVER_PORT);
        Thread.sleep(100);
        List<IMessage> startMessages = new ArrayList<>();
        startMessages.add(client1MessageFactory.createGameInitMessage(new ArrayList<>())); //here would be a list of subways
        startMessages.add(client1MessageFactory.createGameFieldUpdateMessage(-1, new ArrayList<>(), new ArrayList<>())); //were would be a list of mice and players
        this.server.startGame(startMessages);
        Thread.sleep(100);

        while(server.getThread().getState() != Thread.State.WAITING){
            Thread.sleep(100);
        }

        this.client2.getMessages();
        this.client1.getMessages();

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
        IPosition pos1 = new game.core.models.impl.Position(1, 2);
        IPosition pos2 = new game.core.models.impl.Position(3, 4);
        IPosition pos3 = new game.core.models.impl.Position(5, 6);
        IPosition pos4 = new game.core.models.impl.Position(7, 8);
        IPosition pos5 = new game.core.models.impl.Position(0,0);

        IPlayer player1 = new Player(this.client1.getId(), "Player 1", pos1, "cat1.png");
        IPlayer player2 = new Player(this.client2.getId(), "Player 2", pos2, "cat2.png");

        List<IExit> exits = new ArrayList<>();
        exits.add(new Exit(pos5));
        ISubway subway = new Subway(exits);
        List<ISubway> subways = new ArrayList<>();
        subways.add(subway);

        List<IPlayer> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        IMouse mouse1 = new Mouse("ID_MOUSE_1", "mouse.png");
        mouse1.setPosition(pos3);
        IMoveAlgorithm alg1 = new DirectAlgorithm(mouse1, subways, players);
        mouse1.setMoveAlgorithm(alg1);
        IMouse mouse2 = new Mouse("ID_MOUSE_2", "mouse.png");
        mouse2.setPosition(pos4);
        IMoveAlgorithm alg2 = new DirectAlgorithm(mouse1, subways, players);
        mouse2.setMoveAlgorithm(alg2);


        List<IMouse> mice = new ArrayList<>();
        mice.add(mouse1);
        mice.add(mouse2);

        IMessage gameFieldUpdateMessage = this.serverMessageFactory.createGameFieldUpdateMessage(420, players, mice);

        this.server.sendToAllClients(gameFieldUpdateMessage);

        Thread.sleep(100);

        List<IMessage> messages = this.client1.getMessages();

        assertEquals(1, messages.size());
        IMessage message = messages.get(0);
        assertEquals(MessageType.GAME_FIELD_UPDATE, message.getMessageType());

        Map<String, IPosition> playerPos = message.getPlayersPositions();
        Map<String, IPosition> micePos = message.getMicePositions();

        assertEquals(2, playerPos.size());
        assertEquals(2, micePos.size());

        assertTrue(playerPos.keySet().containsAll(Arrays.asList(this.client1.getId(), this.client2.getId())));
        assertTrue(micePos.keySet().containsAll(Arrays.asList("ID_MOUSE_1", "ID_MOUSE_2")));

        assertEquals(pos1, playerPos.get(this.client1.getId()));
        assertEquals(pos2, playerPos.get(this.client2.getId()));

        assertEquals(pos3, micePos.get("ID_MOUSE_1"));
        assertEquals(pos4, micePos.get("ID_MOUSE_2"));

        assertEquals(this.server.getId(), message.getSenderId());
        assertEquals(this.server.getName(), message.getSenderName());
    }

    @Test
    public void testGameInitMessage() throws InterruptedException {

        List<IExit> exits1 = Arrays.asList(new Exit(new game.core.models.impl.Position(1,2)), new Exit(new game.core.models.impl.Position(2,3)));
        List<IExit> exits2 = Arrays.asList(new Exit(new game.core.models.impl.Position(4,5)), new Exit(new game.core.models.impl.Position(6,7)), new Exit(new game.core.models.impl.Position(8,9)));

        ISubway subway1 = new Subway(exits1);
        ISubway subway2 = new Subway(exits2);

        List<ISubway> subways = Arrays.asList(subway1, subway2);

        IPosition pos3 = new game.core.models.impl.Position(5, 6);
        IPosition pos4 = new game.core.models.impl.Position(7, 8);

        IMouse mouse1 = new Mouse("ID_MOUSE_1", "mouse.png");
        IMoveAlgorithm alg1 = new DirectAlgorithm(mouse1, subways, new ArrayList<>());
        mouse1.setPosition(pos3);
        mouse1.setMoveAlgorithm(alg1);
        IMouse mouse2 = new Mouse("ID_MOUSE_2", "mouse.png");
        IMoveAlgorithm alg2 = new DirectAlgorithm(mouse1, subways, new ArrayList<>());
        mouse2.setPosition(pos4);
        mouse2.setMoveAlgorithm(alg2);

        subway1.enter(mouse1);
        subway1.enter(mouse2);

        List<IMouse> mice = new ArrayList<>();
        mice.add(mouse1);
        mice.add(mouse2);

        subway2.setGoal(true);


        IMessage gameInitMessage = this.serverMessageFactory.createGameInitMessage(subways);
        this.server.sendToAllClients(gameInitMessage);
        Thread.sleep(200);
        IMessage message = this.client1.getMessages().get(0);
        assertEquals(0, this.client1.getMessages().size());
        assertEquals(MessageType.GAME_FIELD_INIT, message.getMessageType());
        List<ISubway> recSubways = message.getSubways();
        assertEquals(2, recSubways.size());
        ISubway recSubway1 = recSubways.get(0);
        ISubway recSubway2 = recSubways.get(1);

        assertEquals(2, recSubway1.getExits().size());
        assertEquals(3, recSubway2.getExits().size());

        assertEquals(exits1.stream().map(e -> e.getPosition()).collect(Collectors.toList()), recSubway1.getExits().stream().map(e -> e.getPosition()).collect(Collectors.toList()));
        assertEquals(exits2.stream().map(e -> e.getPosition()).collect(Collectors.toList()), recSubway2.getExits().stream().map(e -> e.getPosition()).collect(Collectors.toList()));

        List<IMouse> recMice = message.getMice();
        assertEquals(2, recMice.size());
        assertEquals(mice.stream().map(m -> m.getId()).collect(Collectors.toList()), recMice.stream().map(m -> m.getId()).collect(Collectors.toList()));

        assertEquals(this.server.getId(), message.getSenderId());
        assertEquals(this.server.getName(), message.getSenderName());

    }

    @Test
    public void testGameInitMessageEmptyList() throws InterruptedException {
        IMessage gameInitMessage = this.serverMessageFactory.createGameInitMessage(new ArrayList<>());
        this.server.sendToAllClients(gameInitMessage);
        Thread.sleep(200);
        IMessage message = this.client2.getMessages().get(0);

        assertEquals(0, this.client2.getMessages().size());
        assertEquals(0, message.getSubways().size());
        assertEquals(this.server.getId(), message.getSenderId());
        assertEquals(this.server.getName(), message.getSenderName());
    }

    @Test
    public void testGameFieldUpdateMessageEmptyLists() throws InterruptedException {
        IMessage gameFieldUpdate = this.serverMessageFactory.createGameFieldUpdateMessage(-1, new ArrayList<>(), new ArrayList<>());
        this.server.sendToAllClients(gameFieldUpdate);
        Thread.sleep(200);
        IMessage message = this.client1.getMessages().get(0);
        assertEquals(0, this.client1.getMessages().size());
        assertEquals(0, message.getPlayersPositions().size());
        assertEquals(0, message.getMicePositions().size());
        assertEquals(this.server.getId(), message.getSenderId());
        assertEquals(this.server.getName(), message.getSenderName());
    }

    @Test
    public void testCatDirectionChangeMessage() throws InterruptedException {
        IPosition pos1 = new game.core.models.impl.Position(1, 2);
        IPlayer player1 = new Player(this.client1.getId(), "Player 1", pos1, "cat1.png");
        IMessage catDirChange = this.client1MessageFactory.createCatDirectionChangeMessage(42, player1, Direction.LEFT);
        this.client1.sendMessage(catDirChange);
        Thread.sleep(100);
        IMessage message = this.server.getMessageQueue().take();

        assertEquals(MessageType.CAT_DIRECTION_CHANGE, message.getMessageType());
        assertEquals(Direction.LEFT, message.getDirection());
        assertEquals(this.client1.getId(), message.getSenderId());
        assertEquals(this.client1.getName(), message.getSenderName());
    }

}
