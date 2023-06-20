package game.core.models;

public interface IPosition {

    float getX();
    float getY();

    IPosition subtract(IPosition position);
    IPosition add(IPosition position);
    IPosition multiply(float factor);

    IPosition divide(float factor);

    float length();
}
