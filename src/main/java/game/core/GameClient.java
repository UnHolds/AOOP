package game.core;

import game.core.models.IGame;
import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.IPosition;
import networking.IMessage;
import networking.client.IClient;

import java.util.Map;

public class GameClient {

    private IClient client;
    private IGame game;

    public GameClient(IClient client, IGame game){
        this.client = client;
        this.game = game;
    }

    private void updateGameField(IMessage message){
        Map<String, IPosition> playerPos = message.getPlayersPositions();
        Map<String, IPosition> micePos = message.getMicePositions();

        for(IPlayer player : this.game.getPlayers()){
            IPosition pos = playerPos.get(player.getId());
            player.setPosition(pos);
        }

        //backup delete should never filter :)
        //this.game.setMice(this.game.getMouses().stream().filter(m -> micePos.containsKey(m.getId())).collect(Collectors.toList()));

        for(IMouse mouse : this.game.getMice()){
            mouse.setPosition(micePos.get(mouse.getId()));
        }
    }

    private void updateScoreBoard(String playerId){
        IPlayer player = this.game.getPlayers().stream().filter(p -> p.getId().equals(playerId)).findFirst().orElse(null);
        player.setScore(player.getScore() + 1);
    }

    private void removeMouse(String id){
        IMouse mouse = this.game.getMice().stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);

        if(mouse == null){
            throw new RuntimeException("Eaten mouse not found");
        }

        this.game.getMice().remove(mouse);
    }

    public void gameLoop(){
        IMessage message = this.client.take();

        switch (message.getMessageType()){
            case GAME_FIELD_UPDATE:
                updateGameField(message);
                break;
            case CAT_EAT_MOUSE_MESSAGE:
                updateScoreBoard(message.getPlayerId());
                removeMouse(message.getMouseId());
                break;
        }
    }
}
