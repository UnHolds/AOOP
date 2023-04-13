package networking;

import networking.client.IClient;
import networking.server.IServer;
import networking.server.IServerClient;

import java.util.Base64;
import java.util.List;

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
    public IMessage createClientConnectMessage() {
        return new Message(MessageType.CLIENT_CONNECT, this.entity.getId(), this.entity.getName(), -1, null);
    }

    public IMessage createConnectedClientsUpdateMessage(List<IServerClient> clients){
        String data = "";

        for(IServerClient client : clients){
            String base64Name = Base64.getEncoder().encodeToString(client.getName().getBytes());
            data += client.getId() + "|" + base64Name + "#";
        }

        data = data.substring(0, data.length()-1);

        return new Message(MessageType.CONNECTED_CLIENTS_UPDATE, this.entity.getId(), this.entity.getName(), -1, data);
    }


}
