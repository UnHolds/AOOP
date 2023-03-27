package networking;

public class Message implements IMessage{

    private MessageType type;

    public Message(byte[] data){

    }

    public Message(){
        this.type = MessageType.NO_MESSAGE;
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public MessageType getMessageType() {
        return this.type;
    }

}
