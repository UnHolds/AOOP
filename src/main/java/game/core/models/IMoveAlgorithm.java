package game.core.models;

import game.core.models.impl.moveAlgorithms.AlgorithmType;

public interface IMoveAlgorithm {

    /**
     * PRE: IMoveAlgorithm is not null and has been initialized with mouse to move, a list of subways and a list of cats
     * POST: The new position of the mouse is returned as an IPosition
     *
     * @return new position of the mouse as IPosition
     */
    IPosition getNextPosition();

    /**
     * PRE: IMoveAlgorithm is not null and algorithm types is set
     * POST: Returns the algorithm type
     *
     * @return the type of the algorithm as AlgorithmType value
     */
    AlgorithmType getType();
}
