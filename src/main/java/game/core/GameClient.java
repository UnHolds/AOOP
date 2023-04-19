package game.core;

import game.controller.GameController;
import game.core.models.ICharacter;
import game.core.models.IGame;
import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.impl.*;
import networking.IMessage;
import networking.IMessageFactory;
import networking.MessageFactory;
import networking.client.Client;
import networking.client.IClient;

import java.io.IOException;
import java.util.*;

public class GameClient {
    GameController controller;
    IGame game;

    IClient networkClient;

    IMessageFactory messageFactory;

    public GameClient(String address, int port) {
        Field field = new Field(12, 18, Set.of(
                new Subway(List.of(new Position(3, 4), new Position(7, 4))))
        );

        List<IPlayer> playerList = new ArrayList<>();
        playerList.add(new Player("fe4b86c0-e2cb-4992-bf54-7c7785df3d2d", 3, "Alice", new Position(1, 2), "cat1.png"));
        //playerList.add(new Player(null, 1, "Bob", new Position(1, 3), "cat2.png"));
        //playerList.add(new Player(null, 2, "Bob", new Position(1, 4), "cat3.png"));
        //playerList.add(new Player(null,4, "Eve", new Position(1, 5), "cat4.png"));

        game = new Game(field, playerList, Set.of());

        networkClient = new Client("asdf");
        messageFactory = new MessageFactory(networkClient);
        try {
            networkClient.connect(address, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void run() {
        while (true) {
            IMessage msg;
            try {
                msg = networkClient.getMessageQueue().take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            switch (msg.getMessageType()) {
                case GAME_FIELD_UPDATE -> {
                    updateCharacterPositions(game.getPlayers(), msg.getPlayersPositions());
                    controller.updateGameWindow();
                }
            }
        }
    }

    public void sendDirectionChange(ICharacter player, Direction direction) {
        networkClient.sendMessage(messageFactory.createCatDirectionChangeMessage(0, player, direction));
    }

    private static void updateCharacterPositions(Collection<? extends ICharacter> characters, Map<String, Position> positions) {
        for (ICharacter character : characters) {
            character.setPosition(positions.get(character.getId()));
        }
    }

    public IGame getGame() {
        return game;
    }
}
