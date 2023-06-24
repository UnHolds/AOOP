package game.core.models;


/**
 * This interface provides the methods the characters (mouse and cat) need in order to move on the game field
 */
public interface ICharacter extends Positionable, Boundable, Drawable {

    /**
     * PRE: Character is not null and character is placed on game board
     * POST: Moves the character object on the game board based on the character's movement algorithm
     */
    void move();

    /**
     * PRE: Character is not null and id is not "" or null
     * POST: Returns the characters id
     *
     * @return the characters id
     */
    String getId();

}
