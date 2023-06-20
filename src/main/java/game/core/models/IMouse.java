package game.core.models;

/**
 * Contains all the mouse specific functionalities such as broadcasting the cat's position to the other mice, how the
 * mouse determines there next move and the deathAnimation
 */
public interface IMouse extends ICharacter{

    void setMoveAlgorithm(IMoveAlgorithm algorithm);
    IMoveAlgorithm getAlgorithm();

    float getSpeed();

}
