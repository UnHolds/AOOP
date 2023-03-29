package networking;

import networking.client.IClient;
import networking.server.IServer;

public class MessageFactory implements IMessageFactory{

    private IClient client;
    public MessageFactory(IClient client){
        this.client = client;
    }

    @Override
    public IMessage createGameTickUpdateMessage(long gameTick) {
        return new Message(this.client, gameTick);
    }

    @Override
    public IMessage createCatPositionMessage(long gameTick, int x, int y, int z) {
        return new Message(MessageType.CAT_POSITION, this.client, gameTick, x + "|" + y + "|" + z);
    }
}
