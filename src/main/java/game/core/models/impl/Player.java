package game.core.models.impl;

import game.core.handler.Direction;
import game.core.models.IPlayer;
import game.core.models.IPosition;

import java.awt.*;

public class Player implements IPlayer {

    private IPosition position;
    private String id;
    private String name;
    private int uiID;

    public Player(String id, String name, IPosition startPosition, String picturePath){
        this.position = startPosition;
        this.id = id;
        this.name = name;
    }

    @Override
    public void move() {

    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int getUiID() {
        return this.uiID;
    }

    @Override
    public void setUiID(int uiID) {
        this.uiID = uiID;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setScore(int score) {

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void addMovingDirection(Direction direction) {

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
