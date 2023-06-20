package networking;

import game.core.handler.Direction;
import game.core.models.ICharacter;
import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.ISubway;
import networking.server.IServerClient;

import java.util.List;

public interface IMessageFactory {

    IMessage createGameTickUpdateMessage(long gameTick);

    IMessage createClientConnectMessage();

    IMessage createConnectedClientsUpdateMessage(List<IServerClient> clients);

    IMessage createGameFieldUpdateMessage(long gameTick, List<IPlayer> players, List<IMouse> mice);

    IMessage createGameInitMessage(List<ISubway> subways, int rowCount, int colCount);

    IMessage createCatDirectionChangeMessage(long gameTick, ICharacter player, Direction direction);

    IMessage createCatEatMouseMessage(long gameTick, IPlayer player, IMouse mouse);
}
