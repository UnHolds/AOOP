package game.core;


import game.core.handler.Direction;
import game.core.models.*;
import game.core.models.impl.Player;
import game.core.models.impl.Position;
import networking.IMessage;
import networking.IMessageFactory;
import networking.MessageFactory;
import networking.server.IServer;
import networking.server.IServerClient;
import networking.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class GameServer implements Runnable{

    private int rowCount;
    private int colCount;

    private static float eatDistance = 0.5f;

    private static long sendInterval = 200;

    private List<IPlayer> players = new ArrayList<>();
    private List<IMouse> mice = new ArrayList<>();
    private List<ISubway> subways = new ArrayList<>();

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
    }

    public void startGame(GameInit gameInit){

        this.rowCount = gameInit.getRowCount();
        this.colCount = gameInit.getColCount();
        this.players = gameInit.getPlayers();
        this.subways = gameInit.getSubways();
        this.mice = gameInit.getMice();

        List<IMessage> startMessages = new ArrayList<>();

        startMessages.add(this.messageFactory.createGameInitMessage(gameInit.getSubways(), this.rowCount, this.colCount));

        //init the start positions
        List<IPosition> startPositions = new ArrayList<>();
        startPositions.add(new Position(0,colCount / 2));
        startPositions.add(new Position(rowCount - 1,colCount / 2));
        startPositions.add(new Position(rowCount / 2,0));
        startPositions.add(new Position(rowCount / 2,colCount - 1));
        for(int i = 0; i < players.size(); i++){
            players.get(i).setPosition(startPositions.get(i));
        }

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
            IPosition mPos = mouse.getPosition();
            for (IPlayer player : this.players) {
                IPosition pos = player.getPosition();
                if(Math.abs(mPos.getX() - pos.getX()) + Math.abs(mPos.getY() - pos.getY()) < eatDistance){
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
