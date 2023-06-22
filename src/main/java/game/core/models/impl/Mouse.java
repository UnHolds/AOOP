package game.core.models.impl;

import game.core.models.IMouse;
import game.core.models.IMoveAlgorithm;
import game.core.models.IPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Mouse implements IMouse {

    private IPosition position;
    private String id;

    private Image image;
    private IMoveAlgorithm algorithm;
    private float minXBound;
    private float minYBound;
    private float maxXBound;
    private float maxYBound;

    private float speed = 1f;

    public Mouse(String id, String picturePath){
        this.id = id;
        this.position = new Position(-1, -1);
        try {
            this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(picturePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void move() {
        IPosition newPosition = this.algorithm.getNextPosition();

        if(newPosition.getX() == -1 && newPosition.getY() == -1){
            this.position = newPosition;
            return;
        }

        this.position = boundsCheck(newPosition);
    }

    @Override
    public Image getImage() {
        return this.image;
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
    public float getSpeed() {
        return this.speed;
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
