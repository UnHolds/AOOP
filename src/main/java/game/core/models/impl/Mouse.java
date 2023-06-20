package game.core.models.impl;

import game.core.models.IMouse;
import game.core.models.IMoveAlgorithm;
import game.core.models.IPosition;

import java.awt.*;

public class Mouse implements IMouse {

    private IPosition position;
    private String id;
    private String picturePath;
    private IMoveAlgorithm algorithm;
    private float minXBound;
    private float minYBound;
    private float maxXBound;
    private float maxYBound;

    public Mouse(String id, String picturePath){
        this.id = id;
        this.picturePath = picturePath;
    }

    @Override
    public void move() {
        IPosition newPosition = this.algorithm.getNextPosition();
        this.position = boundsCheck(newPosition);
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setMoveAlgorithm(IMoveAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public IMoveAlgorithm getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public IPosition getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(IPosition position) {
        this.position = position;
    }

    private IPosition boundsCheck(IPosition newPosition){

        if(newPosition.getX() < this.minXBound){
            newPosition = new Position(this.minXBound, newPosition.getY());
        }else if(newPosition.getX() > this.maxXBound){
            newPosition = new Position(this.maxXBound, newPosition.getY());
        }

        if(newPosition.getY() < this.minYBound){
            newPosition = new Position(newPosition.getX(), this.minYBound);
        }else if(newPosition.getY() > this.maxYBound){
            newPosition = new Position(newPosition.getX(), this.maxYBound);
        }

        return newPosition;
    }

    @Override
    public void setBounds(float minX, float minY, float maxX, float maxY) {
        this.minXBound = minX;
        this.minYBound = minY;
        this.maxXBound = maxX - 1;
        this.maxYBound = maxY - 1;
    }
}
