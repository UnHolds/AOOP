package networking;

public class Message implements IMessage{


    private MessageType type;

    public Message(String dataBase64){

    }

    public Message(){
        this.type = MessageType.NO_MESSAGE;
    }


    @Override
    public String toBase64String() {
        return null;
    }

    @Override
    public MessageType getMessageType() {
        return this.type;
    }

}
