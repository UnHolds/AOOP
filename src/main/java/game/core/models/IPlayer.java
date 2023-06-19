package game.core.models;

import game.core.handler.Direction;

public interface IPlayer extends ICharacter{

    int getScore();

    int getGameUIId();

    void setScore(int score);

    String getName();

    void addMovingDirection(Direction direction);

}
