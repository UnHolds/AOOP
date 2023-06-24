package game.core.models;

import game.core.handler.Direction;

public interface IPlayer extends ICharacter{

    /**
     * PRE: IPLayer is not null
     * POST: Returns the current score of the player
     *
     * @return score of the player as int
     */
    int getScore();

    /**
     * PRE: IPlayer is not null and UiID was set previously
     * POST: Returns the UiID of the player
     *
     * @return UiID of the player
     */
    int getUiID();

    /**
     * PRE: IPlayer is not null, uiID is >= 0
     * POST: The uiID of the player is set to uiID
     */
    void setUiID(int uiID);

    /**
     * PRE: IPlayer is not null, score was set previously
     * POST: Returns the score of the player
     */
    void setScore(int score);

    /**
     * PRE: IPlayer is not null, name was set previously
     * POST: Returns the name of the player
     *
     * @return name of the player as String
     */
    String getName();

    /**
     * PRE: IPlayer is not null, direction was initialized
     * POST: Moving direction was added to list of moving directions
     */
    void addMovingDirection(Direction direction);

}
