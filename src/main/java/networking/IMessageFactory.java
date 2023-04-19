package networking;

import game.core.models.ICharacter;
import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.impl.Direction;
import game.core.models.impl.Subway;
import networking.client.IClient;
import networking.server.IServerClient;

import java.util.List;

public interface IMessageFactory {

    IMessage createGameTickUpdateMessage(long gameTick);

    IMessage createClientConnectMessage();

    IMessage createConnectedClientsUpdateMessage(List<IServerClient> clients);

    IMessage createGameFieldUpdateMessage(long gameTick, List<IPlayer> players, List<IMouse> mice);

    IMessage createGameInitMessage(List<Subway> subways);

    IMessage createCatDirectionChangeMessage(long gameTick, ICharacter player, Direction direction);
}
