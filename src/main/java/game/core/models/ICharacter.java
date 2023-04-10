package game.core.models;

import game.core.Position;

import java.awt.*;

/**
 * This interface provides the methods the characters (mouse and cat) need in order to move on the game field
 */
public interface ICharacter {

    void move(Direction direction);

    Image getImage();

    Position getPosition();


    //void leap(); TODO might be added later

}
