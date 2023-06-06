package game.core;


import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.impl.Player;
import game.core.models.impl.Subway;
import networking.IMessage;
import networking.IMessageFactory;
import networking.MessageFactory;
import networking.client.Client;
import networking.client.IClient;
import networking.server.IServer;
import networking.server.IServerClient;
import networking.server.Server;
import networking.server.ServerClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer implements Runnable{

    public static int rowCount = 1;
    public static int colCount = 1;

    private List<IPlayer> player = new ArrayList<>();
    private List<IMouse> mice = new ArrayList<>();
    private List<Position> startPositions = new ArrayList<>();
    private IServer server;
    private IMessageFactory messageFactory;
    private Thread thread;
    private boolean running = true;

    private static Logger log = LogManager.getLogger(GameServer.class);

    public GameServer(int port) {
        this.server = new Server();
        try {
            this.server.start(port);
            this.messageFactory = new MessageFactory(this.server);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        startPositions.add(new Position(0,rowCount / 2));
        startPositions.add(new Position(colCount - 1,rowCount / 2));
        startPositions.add(new Position(rowCount / 2,0));
        startPositions.add(new Position(rowCount / 2,colCount - 1));

        this.thread = new Thread(this);
        this.thread.start();
    }

    public void startGame(){

        List<IServerClient> serverClients = this.server.getClients();

        for(int i = 0; i < serverClients.size(); i++){
            IServerClient sc = serverClients.get(i);
            player.add(new Player(sc.getId(), i, sc.getName(), startPositions.get(i), "cat1.png"));
        }

        List<IMessage> startMessages = new ArrayList<>();

        List<Subway> subways = new ArrayList<>();

        startMessages.add(this.messageFactory.createGameInitMessage(subways));
        startMessages.add(this.messageFactory.createGameFieldUpdateMessage(-1, player, mice));
        log.info("Sending start messages to clients");
        this.server.startGame(startMessages);
    }




    @Override
    public void run() {
        while(this.running){

        }
    }
}
