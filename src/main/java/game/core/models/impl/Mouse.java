package game.core.models.impl;

import game.core.models.IMouse;
import game.core.models.IMoveAlgorithm;
import game.core.models.IPosition;

import java.awt.*;

public class Mouse implements IMouse {

    private IPosition position;
    private String id;

    public Mouse(String id, String picturePath){
        this.id = id;
    }

    @Override
    public void move() {

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

    }

    @Override
    public IPosition getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(IPosition position) {
        this.position = position;
    }
}
