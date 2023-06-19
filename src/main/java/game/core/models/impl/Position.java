package game.core.models.impl;

import game.core.models.IPosition;

public class Position implements IPosition {


    private static final float COMPARE_DIFF = 0.000001f;

    float x;
    float y;

    public Position(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof IPosition == false){
            return false;
        }

        IPosition pos = (IPosition) obj;

        return Math.abs(this.x - pos.getX()) < COMPARE_DIFF && Math.abs(this.y - pos.getY()) < COMPARE_DIFF;
    }
}
