package game.core;


import game.core.models.IMouse;
import game.core.models.IMoveAlgorithm;
import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.impl.Direction;
import game.core.models.impl.Mouse;
import game.core.models.impl.Player;
import game.core.models.impl.Subway;
import game.core.models.impl.moveAlgorithms.BasicAlgorithm;
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
import java.util.*;

public class GameServer implements Runnable{

    public static int rowCount = 18;
    public static int colCount = 25;

    public static float eatDistance = 0.5f;

    public static long sendInterval = 200;
    private List<IPlayer> players = new ArrayList<>();
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


        startPositions.add(new Position(0,colCount / 2));
        startPositions.add(new Position(rowCount - 1,colCount / 2));
        startPositions.add(new Position(rowCount / 2,0));
        startPositions.add(new Position(rowCount / 2,colCount - 1));
    }

    public void startGame(){

        List<IServerClient> serverClients = this.server.getClients();

        for(int i = 0; i < serverClients.size(); i++){
            IServerClient sc = serverClients.get(i);
            players.add(new Player(sc.getId(), i, sc.getName(), startPositions.get(i), "cat1.png"));
        }

        List<IMessage> startMessages = new ArrayList<>();

        List<Subway> subways = new ArrayList<>();
        Subway subway1 = new Subway(Arrays.asList(new Position(0,0), new Position(rowCount -1, colCount -1)));
        subways.add(subway1);

        Mouse m = new Mouse("ID_MOUSE_0", new Position(5,5), "mouse.png");
        IMoveAlgorithm alg = new BasicAlgorithm(m, -1, 10 , subway1);
        m.setMoveAlgorithm(alg);
        mice.add(m);

        startMessages.add(this.messageFactory.createGameInitMessage(subways));
        startMessages.add(this.messageFactory.createGameFieldUpdateMessage(-1, players, mice));
        log.info("Sending start messages to clients");
        this.server.startGame(startMessages);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.thread = new Thread(this);
        this.thread.start();
    }

    private void updateCatDirection(String id, Direction direction){
        log.info("handling cat direction update message from " + id);
        for(IPlayer player : players){
            if(player.getId().equals(id)){
                player.addMovingDirection(direction);
            }
        }
    }

    private void handleMessage(IMessage message){
        log.info("Handling new message");

        switch (message.getMessageType()){
            case CAT_DIRECTION_CHANGE:
                updateCatDirection(message.getSenderId(), message.getDirection());
                break;
        }
    }

    private void sendGameFieldUpdate(){

        for(IPlayer player: this.players){
            player.move();
        }

        for(IMouse mouse : this.mice){
            mouse.move();
        }

        IMessage message = this.messageFactory.createGameFieldUpdateMessage(0, players, mice);
        this.server.sendToAllClients(message);
    }


    private void checkIfMouseWasEaten(){
        Random rand = new Random();
        List<IMouse> newMice = new ArrayList<>();
        for(IMouse mouse : this.mice) {
            List<IPlayer> playerThatEat = new ArrayList<>();
            Position mPos = mouse.getPosition();
            for (IPlayer player : this.players) {
                Position pos = player.getPosition();
                if(Math.abs(mPos.x() - pos.x()) + Math.abs(mPos.y() - pos.y()) < eatDistance){
                    playerThatEat.add(player);
                }
            }

            if(playerThatEat.size() > 0){
               IPlayer p = playerThatEat.get(rand.nextInt(playerThatEat.size()));
               IMessage message = this.messageFactory.createCatEatMouseMessage(-1, p, mouse);
               log.info("Cat: " + p.getName() + " has eaten mouse: " + mouse.getId());
               this.server.sendToAllClients(message);
            }else{
                newMice.add(mouse);
            }
        }

        this.mice = newMice;
    }


    @Override
    public void run() {

        log.info("Entering run method with new thread");
        long lastUpdate = 0;

        while(this.running){

            if(this.server.getMessageQueue().isEmpty() == false){
                try {
                    handleMessage(this.server.getMessageQueue().take());
                } catch (InterruptedException e) {
                    log.warn("Was interrupted while take from message queue");
                }
            }

            if(lastUpdate + sendInterval <= System.currentTimeMillis()){
                sendGameFieldUpdate();
                checkIfMouseWasEaten();
                lastUpdate = System.currentTimeMillis();
            }

            //do not fry my CPU
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                log.warn("Was interrupted while waiting");
            }
        }
    }
}
