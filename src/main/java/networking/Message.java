package networking;

import game.core.handler.Direction;
import game.core.models.IPosition;
import game.core.models.ISubway;
import game.core.models.impl.Position;

import java.io.*;
import java.util.*;

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
    public Map<String, IPosition> getPlayersPositions() {

        if(this.type != MessageType.GAME_FIELD_UPDATE){
            return null;
        }


        String playersData = this.data.startsWith("@") ? "" : this.data.split("@")[0];

        return getPositionMap(playersData);
    }

    @Override
    public Map<String, IPosition> getMicePositions() {

        if(this.type != MessageType.GAME_FIELD_UPDATE){
            return null;
        }

        String miceData = this.data.endsWith("@") ? "" : this.data.split("@")[1];

        return getPositionMap(miceData);
    }

    private Map<String, IPosition> getPositionMap(String dataString) {

        HashMap<String, IPosition> positions = new HashMap<>();

        if(dataString.isEmpty()){
            return positions;
        }

        for(String mouse : dataString.split("#")){
            String id = mouse.split("~")[0];
            String[] coordinates = mouse.split("~")[1].split("\\|");

            float row = Float.parseFloat(coordinates[0]);
            float column = Float.parseFloat(coordinates[1]);

            IPosition pos = new Position(row, column);

            positions.put(id, pos);
        }

        return positions;
    }

    @Override
    public List<ISubway> getSubways() {

        if(this.type != MessageType.GAME_FIELD_INIT){
            return null;
        }

        if(data.isEmpty()){
            return new ArrayList<>();
        }

        List<ISubway> subways = new ArrayList<>();

        for(String sSubway : this.data.split("@")){
            List<Position> exits = new ArrayList<>();
            for(String sExitPos : sSubway.split("#")){

                String[] coordinates = sExitPos.split("\\|");
                float row = Float.parseFloat(coordinates[0]);
                float column = Float.parseFloat(coordinates[1]);
                //TODO create IExit
                exits.add(new Position(row, column));
            }
            subways.add(new Subway(exits));
        }

        return subways;
    }

    @Override
    public Direction getDirection() {
        if(this.type != MessageType.CAT_DIRECTION_CHANGE){
            return null;
        }

        String[] data = this.data.split("\\|");
        return Direction.values()[Integer.parseInt(data[1])];
    }

    @Override
    public String getMouseId() {
        if(this.type != MessageType.CAT_EAT_MOUSE_MESSAGE){
            return null;
        }

        return this.data.split("\\|")[1];
    }

    @Override
    public String getPlayerId() {
        if(this.type != MessageType.CAT_DIRECTION_CHANGE && this.type != MessageType.CAT_EAT_MOUSE_MESSAGE){
            return null;
        }
        return this.data.split("\\|")[0];
    }


}
