package game.core.models;

import game.core.models.impl.Game;
import game.core.models.impl.Subway;

/**
 * Contains all the mouse specific functionalities such as broadcasting the cat's position to the other mice, how the
 * mouse determines there next move and the deathAnimation
 */
public interface IMouse extends ICharacter{

    /**
     *
     */
    // public void deathAnimation();


    /**
     * Calculate where the mouse moves next based on if it is on the surface or in the tunnel and the moving algorithm
     * the mouse applies
     */
    public void calculateNextMove(Position goal);

    public Position searchNextExit(Subway sub, Position pos);

    public Position closestSubway(Position pos, Game game);

    public void setLastSubway(Integer lastSubway);

    public void adjustDistance();

    public Integer getMoveAmount();

    /**
     * TODO: maybe move somewhere else
     * broadCastCatLocationsToMiceNearby - notifies the other mice about the cat's position given the current mouse
     * is in a tunnel and other mice are in the same place
     */
    // public void broadCastCatLocationsToMiceNearby();


}
