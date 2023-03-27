package game.core;

/**
 * IPosition contains all the methods needed to represent the position of the characters and objects (like the tunnel
 * entrances) in the game.
 */
public interface IPosition {

    static double getDistanceBetween(IGameEntity gameObject1, IGameEntity gameObject2){
        return -1;
    }

    double getXCoordinate();

    double getYCoordinate();

    double setXCoordinate();

    double setYCoordinate();

}
