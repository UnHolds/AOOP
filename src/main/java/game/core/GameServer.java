package game.core;


import game.core.models.impl.Subway;
import networking.IMessage;
import networking.IMessageFactory;
import networking.MessageFactory;
import networking.server.IServer;
import networking.server.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameServer {


    private IServer server;
    private IMessageFactory messageFactory;

    public GameServer(int port) {
        this.server = new Server();
        try {
            this.server.start(port);
            this.messageFactory = new MessageFactory(this.server);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startGame(){

        List<IMessage> startMessages = new ArrayList<>();

        List<Subway> subways = new ArrayList<>();

        startMessages.add(this.messageFactory.createGameInitMessage(subways));

        this.server.startGame(startMessages);
    }
}
