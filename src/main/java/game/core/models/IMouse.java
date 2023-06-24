package game.core.models;

public interface IMouse extends ICharacter{

    /**
     * PRE: Mouse object is not null, algorithm is of type IMoveAlgorithm
     * POST: Sets the mouse movement algorithm to algorithm
     *
     * @param algorithm that is used for mouse movement
     */
    void setMoveAlgorithm(IMoveAlgorithm algorithm);

    /**
     * PRE: Mouse object is not null
     * POST: Returns mouse movement algorithm if it was previously set or null otherwise
     *
     * @return the mouse movement algorithm or null
     */
    IMoveAlgorithm getAlgorithm();

    /**
     * PRE: Mouse object is not null, speed has been initialized to a value > 0
     * POST: Returns the speed of the mouse movement
     *
     * @return the speed of the mouse
     */
    float getSpeed();

}
