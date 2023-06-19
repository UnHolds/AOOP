package game.core.models;

import java.awt.*;

/**
 * This interface provides the methods the characters (mouse and cat) need in order to move on the game field
 */
public interface ICharacter {

    void move();

    Image getImage();

    IPosition getPosition();

    void setPosition(IPosition position);

    String getId();


    //void leap(); TODO might be added later

}
