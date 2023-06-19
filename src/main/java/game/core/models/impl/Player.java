package game.core.models.impl;

import game.core.handler.Direction;
import game.core.models.IPlayer;
import game.core.models.IPosition;

import java.awt.*;

public class Player implements IPlayer {


    public Player(String id, String name, IPosition startPosition, String picturePath){

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
        return null;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int getGameUIId() {
        return 0;
    }

    @Override
    public void setScore(int score) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void addMovingDirection(Direction direction) {

    }

    @Override
    public IPosition getPosition() {
        return null;
    }

    @Override
    public void setPosition(IPosition position) {

    }
}
