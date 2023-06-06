import game.core.GameClient;
import game.core.GameServer;
import game.ui.GameWindow;
import game.ui.IUiGameConfig;
import game.ui.UiGameConfig;
import networking.IMessage;
import networking.Message;
import networking.MessageType;
import networking.client.Client;
import networking.client.IClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        Map<String,String> connectedClients = new HashMap<>();
        while(true){
            message = client.take();

            if(message.getMessageType() == MessageType.GAME_FIELD_INIT){
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

        gw.showGameFieldPanel();
        GameClient gameClient = new GameClient(client);
    }
}