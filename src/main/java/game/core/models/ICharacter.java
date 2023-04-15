package game.core.models;

import game.core.models.impl.Direction;

import java.awt.*;

/**
 * This interface provides the methods the characters (mouse and cat) need in order to move on the game field
 */
public interface ICharacter {

    void move();

    Image getImage();

    Position getPosition();

    void setMovingDirection(Direction direction);

    Direction getMovingDirection();

    String getId();


    //void leap(); TODO might be added later

}
