package game.core.models;

import java.util.List;

public interface IGame {

    /**
     * PRE: IGame is not null
     * POST: Play field as IField or null
     *
     * @return IField play field or null
     */
    IField getField();

    /**
     * PRE: IGame is not null
     * POST: List of current players as IPlayer objects or null
     *
     * @return list of IPlayer objects
     */
    List<IPlayer> getPlayers();

    /**current
     * PRE: IGame is not null
     * POST: List of mice in the game IMouse objects or null
     *
     * @return list of IMouse objects
     */
    List<IMouse> getMice();
}
