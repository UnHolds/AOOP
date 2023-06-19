package game.core.models.impl;

import game.core.models.IPosition;

public class Position implements IPosition {

    float x;
    float y;

    public Position(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }
}
