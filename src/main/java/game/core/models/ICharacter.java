package game.core.models;

import java.awt.*;

/**
 * This interface provides the methods the characters (mouse and cat) need in order to move on the game field
 */
public interface ICharacter extends Positionable, Boundable, Drawable {

    void move();

    String getId();

}
