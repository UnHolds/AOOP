package game.core.models;

public interface Positionable {

     /**
     * PRE: Positional object is not null
     * POST: Returns the position of the object
     *
     * @return the position of the positional object
     */
    IPosition getPosition();

    /**
     * PRE: Positional object is not null
     * POST: Returns the position of the object
     *
     * @param position the position of the object
     */
    void setPosition(IPosition position);
}
