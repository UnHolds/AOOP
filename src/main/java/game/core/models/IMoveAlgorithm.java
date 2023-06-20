package game.core.models;

import game.core.models.impl.moveAlgorithms.AlgorithmType;

public interface IMoveAlgorithm {
    void move();

    AlgorithmType getType();
}
