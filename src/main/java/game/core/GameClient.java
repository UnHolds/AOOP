package game.core;

import game.core.handler.CharacterMovementController;
import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.impl.Game;
import game.ui.GameWindow;
import game.ui.IUiGameConfig;
import networking.IMessage;
import networking.client.Client;
import networking.client.IClient;

import java.io.IOException;
import java.util.Map;

public class GameClient {

    private IClient client;
    private Game game;

    public GameClient(IClient client, Game game){
        this.client = client;
        this.game = game;
    }


    private void updateGameField(IMessage message){
        Map<String, Position> playerPos = message.getPlayersPositions();
        Map<String, Position> micePos = message.getMicePositions();

        for(IPlayer player : this.game.getPlayers()){
            Position pos = playerPos.get(player.getId());
            player.setPosition(pos);
        }

        for(IMouse mouse : this.game.getMouses()){
            mouse.move();
        }
    }

    public void gameLoop(){
        IMessage message = this.client.take();

        switch (message.getMessageType()){
            case GAME_FIELD_UPDATE:
                updateGameField(message);
                break;
        }
    }
}
