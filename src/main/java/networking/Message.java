package networking;

public class Message implements IMessage{

    public Message(byte[] data){

    }

    public Message(){

    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
