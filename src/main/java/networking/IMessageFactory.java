package networking;

import game.core.models.IMouse;
import game.core.models.IPlayer;
import networking.client.IClient;
import networking.server.IServerClient;

import java.util.List;

public interface IMessageFactory {

    IMessage createGameTickUpdateMessage(long gameTick);

    IMessage createClientConnectMessage();

    IMessage createConnectedClientsUpdateMessage(List<IServerClient> clients);

    IMessage createGameFieldUpdateMessage(long gameTick, List<IPlayer> players, List<IMouse> mice);
}
