package networking;

import networking.client.IClient;
import networking.server.IServer;

import java.io.*;
import java.util.Base64;

public class Message implements IMessage, Serializable {


    private MessageType type;
    private long currentGameTick;

    private String senderId;
    private String senderName;


    public static IMessage parse(String base64) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(base64);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Message) ois.readObject();
        }
    }


    public Message(IClient client, long currentGameTick){
        this.type = MessageType.GAME_TICK_UPDATE;
        this.currentGameTick = currentGameTick;
        if(client != null) {
            this.senderId = client.getId();
            this.senderName = client.getName();
        }else {
            this.senderId = "server";
            this.senderName = "server";
        }
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

}
