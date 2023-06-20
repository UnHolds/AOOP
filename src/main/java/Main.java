import game.core.GameClient;
import game.core.GameServer;
import game.core.handler.CharacterMovementController;
import game.core.models.*;
import game.core.models.impl.Mouse;
import game.core.models.impl.Player;
import game.ui.GameWindow;
import game.ui.IUiGameConfig;
import networking.IMessage;
import networking.MessageType;
import networking.client.Client;
import networking.client.IClient;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        GameWindow gw = new GameWindow();
        gw.initWindow();
        IUiGameConfig uiGameConfig = gw.getGameConfig();
        GameServer server;

        if(uiGameConfig.isHost()){
            server = new GameServer(uiGameConfig.getPort());

            Thread thread = new Thread(() -> {
                gw.getGameConfig();
                server.startGame();
            });

            thread.start();
        }

        IClient client = new Client(uiGameConfig.getPlayerName());
        try {
            client.connect(uiGameConfig.getIp(), uiGameConfig.getPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        IMessage message;
        List<IPlayer> players = new ArrayList<>();
        while(true){
            message = client.take();

            if(message.getMessageType() == MessageType.GAME_FIELD_INIT){
                List<ISubway> subways = message.getSubways();
                List<IMouse> mice = message.getMice();
                break;

            }else if(message.getMessageType() == MessageType.CONNECTED_CLIENTS_UPDATE){
                players = message.getPlayers();
            }
        }

        IPlayer selfPlayer = players.stream().filter(p -> p.getId().equals(client.getId())).findFirst().orElse(null);

        if(selfPlayer == null){
            throw new RuntimeException("Could not find self player");
        }

        //gw.setGame(game);

        CharacterMovementController movementController = new CharacterMovementController(client, selfPlayer);
        gw.registerMovementListener(movementController);

        gw.showGameFieldPanel();

        //GameClient gameClient = new GameClient(client, game);


        while(true){
            //gameClient.gameLoop();
            gw.update();
        }

    }
}