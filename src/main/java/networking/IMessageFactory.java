package networking;

import networking.client.IClient;
import networking.server.IServerClient;

import java.util.List;

public interface IMessageFactory {

    IMessage createGameTickUpdateMessage(long gameTick);

    IMessage createCatPositionMessage(long gameTick, int x, int y, int z);

    IMessage createClientConnectMessage();

    IMessage createConnectedClientsUpdateMessage(List<IServerClient> clients);

}
