package game.core.models;

public interface IPosition {

    /**
     * PRE: IPosition is not null, x value was set previously
     * POST: Return the x value of the position
     *
     * @return x value of position as float
     */
    float getX();

    /**
     * PRE: IPosition is not null, y value was set previously
     * POST: Return the y value of the position
     *
     * @return y value of position as float
     */
    float getY();

    /**
     * PRE: IPosition is not null, provided position is not null
     * POST: Returns a new position calculated by subtracting the provided position from this position
     *
     * @param position  IPosition that should be subtracted from current position
     * @return new position
     */
    IPosition subtract(IPosition position);

    /**
     * PRE: IPosition is not null, provided position is not null
     * POST: Returns a new position calculated by adding the provided position from this position
     *
     * @param position IPosition that should be added to the current position
     * @return new position
     */
    IPosition add(IPosition position);

    /**
     * PRE: IPosition is not null,  provided factor is not 0
     * POST: Returns a new position calculated by multiplying the provided factor with the position
     *
     * @param factor
     * @return new position
     */
    IPosition multiply(float factor);

    /**
     * PRE: IPosition is not null, provided factor is not 0
     * POST: Returns a new position calculated by dividing the position by the provided factor
     *
     * @param factor
     * @return new position
     */
    IPosition divide(float factor);

    /**
     * PRE: IPosition is not null, x value and y value are initialized
     * POST: Returns length of the vector from the coordinate origin to the position
     *
     * @return length of the vector from the coordinate origin to the position
     */
    float length();
}
