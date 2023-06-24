package game.core.models;

public interface Boundable {

    /**
     * PRE: Boundable object is not null
     * POST: the bounds of the object are set to minX and minY for the minimum
     * values and to maxX and maxY for the maximum values
     *
     * @param minX Minimal allowed value that is in bounds in x direction
     * @param minY Minimal allowed value that is in bounds in y direction
     * @param maxX Maximal allowed value that is in bounds in x direction
     * @param maxY Maximal allowed value that is in bounds in y direction
     */
    void setBounds(float minX, float minY, float maxX, float maxY);
}
