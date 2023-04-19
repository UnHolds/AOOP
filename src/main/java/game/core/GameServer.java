package game.core;

import game.core.models.*;
import game.core.models.impl.Field;
import game.core.models.impl.Game;
import game.core.models.impl.Player;
import game.core.models.impl.Subway;
import networking.IMessage;
import networking.IMessageFactory;
import networking.MessageFactory;
import networking.server.IServer;
import networking.server.Server;

import java.io.IOException;
import java.util.*;

public class GameServer implements Runnable {
    private static final int TICK_PAUSE_MILLIS = 25;

    private int port;

    private IGame game;

    private IServer networkServer;

    private IMessageFactory messageFactory;

    private Thread messageHandlerThread;


    public GameServer(int port) {
        this.port = port;
        Field field = new Field(12, 18, Set.of(
                new Subway(List.of(new Position(3, 4), new Position(7, 4))))
        );

        List<IPlayer> playerList = new ArrayList<>();
        playerList.add(new Player("fe4b86c0-e2cb-4992-bf54-7c7785df3d2d", 3, "Alice", new Position(1, 2), "cat1.png"));
        //playerList.add(new Player("37ca0347-3b4b-4f02-92bd-49d5025cc590", 1, "Bob", new Position(1, 3), "cat2.png"));
        //playerList.add(new Player("a9e0069a-f36e-4cd4-8c8c-fd4fcdd52087", 2, "Bob", new Position(1, 4), "cat3.png"));
        //playerList.add(new Player("efd238b5-42c4-4ae2-95a8-9945ace07aaa",4, "Eve", new Position(1, 5), "cat4.png"));

        game = new Game(field, playerList, Set.of());

        networkServer = new Server();
        messageFactory = new MessageFactory(networkServer);
    }

    public GameServer() {
        this(12345);
    }

    public void run() {
        messageHandlerThread = new Thread(new ClientMessageHandler());
        messageHandlerThread.start();

        try {
            networkServer.start(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            for(ICharacter character : game.getMouses()) {
                character.move();
            }

            for(ICharacter character : game.getPlayers()) {
                synchronized (character) {
                    character.move();
                }
            }

            // TODO: sent updates to client
            networkServer.sendToAllClients(messageFactory.createGameFieldUpdateMessage(0, game.getPlayers(), List.of(game.getMouses().toArray(new IMouse[0]))));

            try {
                Thread.sleep(TICK_PAUSE_MILLIS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public IGame getGame() {
        return game;
    }

    private class ClientMessageHandler implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    IMessage msg = networkServer.getMessageQueue().take();
                    switch (msg.getMessageType()) {
                        case CLIENT_CONNECT -> {
                            // TODO: synchronization here
                            game.getPlayers().add(new Player(msg.getPlayerId(), 0, msg.getSenderName(), new Position(0, 0), "cat1.png"));
                        }
                        case CAT_DIRECTION_CHANGE -> {
                            IPlayer player = game.getPlayers().stream().filter(iPlayer -> iPlayer.getId().equals(msg.getPlayerId())).findFirst().orElse(null);
                            System.out.println(player);
                            if(player != null) {
                                synchronized (player) {
                                    player.setMovingDirection(msg.getDirection());
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.run();
    }
}
