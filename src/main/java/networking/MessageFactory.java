package networking;

import networking.client.IClient;
import networking.server.IServer;

public class MessageFactory implements IMessageFactory{

    private INetworkEntity entity;

    public MessageFactory(INetworkEntity entity){
        this.entity = entity;
    }

    @Override
    public IMessage createGameTickUpdateMessage(long gameTick) {
        return new Message(this.entity.getId(), this.entity.getName(), gameTick);
    }

    @Override
    public IMessage createCatPositionMessage(long gameTick, int x, int y, int z) {
        return new Message(MessageType.CAT_POSITION, this.entity.getId(), this.entity.getName(), gameTick, x + "|" + y + "|" + z);
    }

    @Override
    public IMessage createClientConnectMessage() {
        return new Message(MessageType.CLIENT_CONNECT, this.entity.getId(), this.entity.getName(), -1, null);
    }


}
