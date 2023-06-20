package game.core.models;

import game.core.models.impl.moveAlgorithms.AlgorithmType;

public interface IMoveAlgorithm {
    IPosition getNextPosition();

    AlgorithmType getType();
}
