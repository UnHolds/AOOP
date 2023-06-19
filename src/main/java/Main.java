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

        IPlayer selfPlayer = null;

        IMessage message;
        Map<String,String> connectedClients = new HashMap<>();
        while(true){
            message = client.take();

            if(message.getMessageType() == MessageType.GAME_FIELD_INIT){
                List<ISubway> subways = message.getSubways();
                message = client.take();
                if(message.getMessageType() != MessageType.GAME_FIELD_UPDATE){
                    throw new RuntimeException("Wrong init procedure");
                }

                Map<String, IPosition> playerPos = message.getPlayersPositions();
                Map<String, IPosition> micePos = message.getMicePositions();

                Map<Integer, ISubway> subwaysMap = new HashMap<>();
                for(int i = 0; i < subways.size(); i++){
                    subwaysMap.put(i, subways.get(i));
                }

                IField field = new Field(GameServer.rowCount,GameServer.colCount, subwaysMap);
                List<IPlayer> players = new ArrayList<>();
                int index = 0;
                for(Map.Entry<String, String> entry : connectedClients.entrySet()){
                    IPlayer p = new Player(entry.getKey(), entry.getValue(), playerPos.get(entry.getKey()), "cat" + (index + 1) +".png");
                    if(entry.getKey().equals(client.getId())){
                        selfPlayer = p;
                    }
                    index++;
                    players.add(p);
                }

                Set<IMouse> mice = new HashSet<>();
                for(int i = 0; i < micePos.size(); i++){

                    IMouse m = new Mouse("ID_MOUSE_" + i, micePos.get("" + i), "mouse.png");
                    IMoveAlgorithm alg = new BasicAlgorithm(m, -1, 10 , null);
                    m.setMoveAlgorithm(alg);
                    mice.add(m);
                }
                game = new Game(field, players, mice);

                break;
            }else if(message.getMessageType() == MessageType.CONNECTED_CLIENTS_UPDATE){
                Map<String, String> clientUpdate = message.getClientIdAndName();

                for(Map.Entry<String, String> entry : clientUpdate.entrySet()){
                    if(connectedClients.containsKey(entry.getKey()) == false){
                        uiGameConfig.addPlayer(entry.getValue());
                        connectedClients.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        gw.setGame(game);

        CharacterMovementController movementController = new CharacterMovementController(client, selfPlayer);
        gw.registerMovementListener(movementController);

        gw.showGameFieldPanel();

        GameClient gameClient = new GameClient(client, game);


        while(true){
            gameClient.gameLoop();
            gw.update();
        }

    }
}