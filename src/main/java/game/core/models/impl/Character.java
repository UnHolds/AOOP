package game.core.models.impl;

import game.core.models.Position;
import game.core.models.ICharacter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Character implements ICharacter {
    private Position position;
    private List<Direction> movingDirections = new ArrayList<>();
    private Image image;
    private String id;

    public Character(String id, Position position, String imagePath) {
        this.position = position;
        this.image = loadImageFromResources(imagePath);
        this.id = id;
    }


    public Character(Position position, String imagePath) {
        this(UUID.randomUUID().toString(), position, imagePath);
    }

    @Override
    public void move() {
        for(Direction dir : movingDirections){
            position =  position.move(dir);
        }
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void addMovingDirection(Direction direction) {
        switch (direction){

            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
                if(this.movingDirections.contains(direction) == false){
                    this.movingDirections.add(direction);
                }
                break;
            case STOP:
                this.movingDirections = new ArrayList<>();
                break;
            case UP_STOP:
                this.movingDirections.remove(Direction.UP);
                break;
            case DOWN_STOP:
                this.movingDirections.remove(Direction.DOWN);
                break;
            case LEFT_STOP:
                this.movingDirections.remove(Direction.LEFT);
                break;
            case RIGHT_STOP:
                this.movingDirections.remove(Direction.RIGHT);
                break;
        }
    }

    @Override
    public String getId() {
        return this.id;
    }

    private Image loadImageFromResources(String imagePath) {
        try {
            return ImageIO.read(getClass().getClassLoader().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace(); // TODO use another way of error handling
            throw new RuntimeException(e);
        }
    }
}
