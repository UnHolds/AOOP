import game.core.GameClient;
import game.core.GameInit;
import game.core.GameServer;
import game.core.handler.CharacterMovementController;
import game.core.models.*;
import game.core.models.impl.Game;
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
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        GameWindow gw = new GameWindow();
        GameInit gameInit = new GameInit();
        gameInit.setGameTime(12);
        gw.initWindow();
        IUiGameConfig uiGameConfig = gw.getGameConfig();
        GameServer server;

        if(uiGameConfig.isHost()){
            server = new GameServer(uiGameConfig.getPort());

            Thread thread = new Thread(() -> {
                gw.getGameConfig(); //blocks until host game button is pressed
                server.startGame(gameInit);
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
        List<ISubway> subways;
        List<IMouse> mice;
        int rowCount;
        int colCount;
        while(true){
            message = client.take();

            if(message.getMessageType() == MessageType.GAME_FIELD_INIT){
                subways = message.getSubways();
                mice = message.getMice();
                rowCount = message.getRowCount();
                colCount = message.getColCount();
                break;

            }else if(message.getMessageType() == MessageType.CONNECTED_CLIENTS_UPDATE){
                List<IPlayer> finalPlayers = players;
                IPlayer newPlayer = message.getPlayers().stream().filter(p -> finalPlayers.stream().map(pp -> pp.getId()).collect(Collectors.toList()).contains(p.getId()) == false).findFirst().orElse(null);
                uiGameConfig.addPlayer(newPlayer.getName());
                players = message.getPlayers();
                gameInit.setPlayers(players);
            }
        }

        IPlayer selfPlayer = players.stream().filter(p -> p.getId().equals(client.getId())).findFirst().orElse(null);

        if(selfPlayer == null){
            throw new RuntimeException("Could not find self player");
        }

        IGame game = new Game(players, mice, subways, rowCount, colCount);

        gw.setGame(game);

        CharacterMovementController movementController = new CharacterMovementController(client, selfPlayer);
        gw.registerMovementListener(movementController);

        gw.showGameFieldPanel();
        GameClient gameClient = new GameClient(client, game);


        while(gameClient.isGameOver() == false){
            gameClient.gameLoop();
            gw.update();
        }

        gw.showWinScreenPanel();

    }
}