package game.core.models.impl;

import game.core.handler.Direction;
import game.core.models.IPlayer;
import game.core.models.IPosition;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayer {

    private IPosition position;
    private String id;
    private String name;
    private int uiID;

    private List<Direction> directions = new ArrayList();

    private static float speed = 0.1f;

    private Image image;

    public Player(String id, String name, IPosition startPosition, String picturePath){
        this.position = startPosition;
        this.id = id;
        this.name = name;
        try {
            this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(picturePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void move() {
        for(Direction direction : this.directions){
            switch (direction) {
                case LEFT:
                    this.position = new Position(this.position.getX() - this.speed, this.position.getY());
                    break;
                case DOWN:
                    this.position = new Position(this.position.getX(), this.position.getY() + this.speed);
                    break;
                case UP:
                    this.position = new Position(this.position.getX(), this.position.getY() - this.speed);
                    break;
                case RIGHT:
                    this.position = new Position(this.position.getX() + this.speed, this.position.getY());
                    break;
            }
        }
    }

    @Override
    public Image getImage() {
        return this.image;
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
        switch (direction){
            case LEFT:
            case DOWN:
            case UP:
            case RIGHT:
                if(this.directions.contains(direction) == false){
                    this.directions.add(direction);
                }
                break;
            case UP_STOP:
                this.directions.remove(Direction.UP);
                break;
            case DOWN_STOP:
                this.directions.remove(Direction.DOWN);
                break;
            case LEFT_STOP:
                this.directions.remove(Direction.LEFT);
                break;
            case RIGHT_STOP:
                this.directions.remove(Direction.RIGHT);
                break;
        }
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
