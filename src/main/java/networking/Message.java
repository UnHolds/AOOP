package networking;

import game.core.models.Position;
import networking.client.IClient;
import networking.server.IServer;

import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Message implements IMessage, Serializable {


    private MessageType type;
    private long currentGameTick;

    private String senderId;
    private String senderName;

    private String data = "";


    public static IMessage parse(String base64) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(base64);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Message) ois.readObject();
        }
    }


    public Message(String senderId, String senderName, long currentGameTick){
        this.type = MessageType.GAME_TICK_UPDATE;
        this.currentGameTick = currentGameTick;
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public Message(MessageType type, String senderId, String senderName, long currentGameTick, String data){
        this.type = MessageType.GAME_TICK_UPDATE;
        this.currentGameTick = currentGameTick;
        this.data = data;
        this.type = type;
        this.senderId = senderId;
        this.senderName = senderName;
    }



    @Override
    public String toBase64String() throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(this);
            oos.flush();
            byte[] byteData = bos.toByteArray();
            return Base64.getEncoder().encodeToString(byteData);
        }
    }

    @Override
    public MessageType getMessageType() {
        return this.type;
    }

    @Override
    public String getSenderId() {
        return this.senderId;
    }

    @Override
    public String getSenderName() {
        return this.senderName;
    }

    @Override
    public long getCurrentGameTick() {
        return this.currentGameTick;
    }

    @Override
    public Map<String, String> getClientIdAndName() {

        if(this.type != MessageType.CONNECTED_CLIENTS_UPDATE){
            return null;
        }

        HashMap<String, String> clients = new HashMap<>();

        for(String clientData : data.split("#")){
            String[] clientIdAndName = clientData.split("\\|");
            String name = new String(Base64.getDecoder().decode(clientIdAndName[1]));
            clients.put(clientIdAndName[0], name);
        }

        return clients;
    }

    @Override
    public Map<String, Position> getPlayersPositions() {

        if(this.type != MessageType.GAME_FIELD_UPDATE){
            return null;
        }

        HashMap<String, Position> playerPositions = new HashMap<>();

        for(String player : this.data.split("@")[0].split("#")){

            String id = player.split("~")[0];
            String[] coordinates = player.split("~")[1].split("\\|");

            int row = Integer.parseInt(coordinates[0]);
            int column = Integer.parseInt(coordinates[1]);

            Position pos = new Position(row, column);

            playerPositions.put(id, pos);
        }

        return playerPositions;
    }

    @Override
    public Map<String, Position> getMicePositions() {

        if(this.type != MessageType.GAME_FIELD_UPDATE){
            return null;
        }

        HashMap<String, Position> micePositions = new HashMap<>();

        for(String mouse : this.data.split("@")[1].split("#")){
            String id = mouse.split("~")[0];
            String[] coordinates = mouse.split("~")[1].split("\\|");

            int row = Integer.parseInt(coordinates[0]);
            int column = Integer.parseInt(coordinates[1]);

            Position pos = new Position(row, column);

            micePositions.put(id, pos);
        }

        return micePositions;
    }

}
