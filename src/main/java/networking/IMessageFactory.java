package networking;

import game.core.handler.Direction;
import game.core.models.ICharacter;
import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.ISubway;
import networking.server.IServerClient;

import java.util.List;

public interface IMessageFactory {

    /**
     * PRE: IMessageFactory is not null, NetworkEntity is not null and initialized
     * POST: Returns a game tick update message containing entity id, entity name and gameTick
     *
     * @param gameTick
     * @return game tick update message
     */
    IMessage createGameTickUpdateMessage(long gameTick);


    /**
     * PRE:  IMessageFactory is not null, NetworkEntity is not null and initialized
     * POST:  Returns a client connect message containing client connect message type, entity id, entity name, gameTick set to -1 and null as data
     *
     * @return client connect message
     */
    IMessage createClientConnectMessage();

    /**
     * PRE:  IMessageFactory is not null, NetworkEntity is not null and initialized
     * POST:  Returns a connected clients update message containing connected clients update message type, entity id, entity name, gameTick set to -1 and clients as data
     *
     * @param clients
     * @return connected clients update message
     */
    IMessage createConnectedClientsUpdateMessage(List<IServerClient> clients);


    /**
     * PRE:  IMessageFactory is not null, NetworkEntity is not null and initialized, players and mice are not null
     * POST:  Returns a game field update message containing GAME_FIELD_UPDATE message type, entity id, entity name, current
     * game tick and player and mice info as data
     *
     * @param gameTick
     * @param players
     * @param mice
     * @return game field update message
     */
    IMessage createGameFieldUpdateMessage(long gameTick, List<IPlayer> players, List<IMouse> mice);

    /**
     * PRE:  IMessageFactory is not null, NetworkEntity is not null and initialized, subways is not null
     * POST:  Returns a game field init message containing GAME_FIELD_INIT message type, entity id, entity name,
     * and game field info as data
     *
     * @param subways
     * @param rowCount
     * @param colCount
     * @return game field init message
     */
    IMessage createGameInitMessage(List<ISubway> subways, int rowCount, int colCount);

    /**
     * PRE:  IMessageFactory is not null, NetworkEntity is not null and initialized, player and direction are not null
     * POST:  Returns a cat direction change message containing CAT_DIRECTION_CHANGE message type, entity id, entity name,
     * current game tick and player id plus direction info
     *
     * @param gameTick
     * @param player
     * @param direction
     * @return cat direction change message
     */
    IMessage createCatDirectionChangeMessage(long gameTick, ICharacter player, Direction direction);

    /**
     * PRE:  IMessageFactory is not null, NetworkEntity is not null and initialized, player and mouse are not null
     * POST:  Returns a cat eat mouse message containing CAT_EAT_MOUSE_MESSAGE message type, entity id, entity name,
     * current game tick and player id mouse info
     *
     * @param gameTick
     * @param player
     * @param mouse
     * @return cat eat mouse message
     */
    IMessage createCatEatMouseMessage(long gameTick, IPlayer player, IMouse mouse);

    /**
     * PRE:  IMessageFactory is not null, NetworkEntity is not null and initialized
     * POST:  Returns a game over message containing GAME_OVER message type, entity id, entity name,
     * game tick set to -1 and empty data ("")
     *
     * @return game over message
     */
    IMessage createGameOverMessage();
}
