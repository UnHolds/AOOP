package networking;

import game.core.handler.Direction;
import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.IPosition;
import game.core.models.ISubway;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IMessage {


    /**
     * PRE: Message object is not null and has been initialized
     * POST: Returns a base64 encoded string message representation that can be sent via the network if the
     * message is encodable in base64 or throws an IOException otherwise
     *
     * @return base64 encoded representation of the message
     * @throws IOException if message is not encodable
     */
    String toBase64String() throws IOException;

    /**
     * PRE: Message object is not null and message type field has been initialized
     * POST: Returns the message type of type MessageType
     *
     * @return the type of the message
     */
    MessageType getMessageType();

    /**
     * PRE:  Message object is not null and senderId field has been initialized
     * POST: Returns the id of the client that has sent the message
     *
     * @return id of the client that has sent the message
     */
    String getSenderId();

    /**
     * PRE:  Message object is not null and senderName field has been initialized
     * POST: Returns the name of the client that has sent the message
     *
     * @return name of the client that has sent the message
     */
    String getSenderName();

    /**
     * PRE: Message object is not null and currentGameTick field has been initialized
     * POST: Returns the currentGameTick value of the message
     *
     * @return the currentGameTick (when the message was sent)
     */
    long getCurrentGameTick();

    /**
     * PRE: Message object is not null and data field is not "" or null and data contains player information
     * POST: Returns a list of IPlayer objects extracted from the data
     *
     * @return a list of iPlayer objects
     */
    List<IPlayer> getPlayers();

    /**
     * PRE: Message object is not null and data field is not "" or null and data contains player positions
     * POST: Returns a map of player id to positions of these players if game type is not GAME_FIELD_UPDATE
     *
     * @return map of player id to position or null
     */
    Map<String, IPosition> getPlayersPositions();

    /**
     * PRE: Message object is not null and data field is not "" or null and data contains mice position
     * POST: Returns a map of mice ids to positions of these mice or null if game type is not GAME_FIELD_UPDATE
     *
     * @return map of mice ids to position or null
     */
    Map<String, IPosition> getMicePositions();

    /**
     * PRE: Message object is not null and data field is not "" or null and data contains subway info
     * POST: Returns a list of all subways in the game or if game type is not GAME_FIELD_INIT
     *
     * @return list of subways or null
     */
    List<ISubway> getSubways();

    /**
     * PRE: Message object is not null and data field is not "" or null and contains movement direction
     * POST: Returns the direction of movement for a cat or null if message type is not CAT_DIRECTION_CHANGE
     *
     * @return moving direction of cat or null
     */
    Direction getDirection();

    /**
     * PRE: Message object is not null and data field is not "" or null and contains mouse id
     * POST: Returns the mouse id of the mouse that is eaten or null if message type is not CAT_EAT_MOUSE_MESSAGE
     *
     * @return mouse id of mouse eaten or null
     */
    String getMouseId();

    /**
     * PRE: Message object is not null and data field is not "" or null and contains player id
     * POST: Returns the player id of the cat that changes direction or eats a mouse or null if message type
     * is not CAT_EAT_MOUSE_MESSAGE or CAT_DIRECTION_CHANGE
     *
     * @return player id of cat changing direction or eating a mouse or null
     */
    String getPlayerId();

    /**
     * PRE: Message object is not null and data field is not "" or null and data contains mice info
     * POST: Returns a list of all mice in the game or null if game type is not GAME_FIELD_INIT
     *
     * @return list of mice or null
     */
    List<IMouse> getMice();

    /**
     * PRE: Message object is not null and data field is not "" or null and data contains row info
     * POST: Returns number of rows in the playing field or -1 if message type is not GAME_FIELD_INIT
     *
     * @return Number of rows or -1 if message type is not GAME_FIELD_INIT
     */
    int getRowCount();

    /**
     * PRE: Message object is not null and data field is not "" or null and data contains column info
     * POST: Returns number of columns in the playing field or -1 if message type is not GAME_FIELD_INIT
     *
     * @return Number of columns or -1 if message type is not GAME_FIELD_INIT
     */
    int getColCount();
}
