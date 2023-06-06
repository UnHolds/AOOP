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
