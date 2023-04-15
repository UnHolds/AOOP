package game.core.models.impl;

import game.core.models.Position;
import game.core.models.ICharacter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Character implements ICharacter {
    private Position position;
    private Direction movingDirection = Direction.STOP;
    private Image image;
    private String id;

    public Character(String id, Position position, String imagePath) {
        this.position = position;
        this.image = loadImageFromResources(imagePath);
        this.id = id;
    }

    public void move() {
        position =  position.move(movingDirection);
    }

    @Override
    public Image getImage() {
        return image;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public void setMovingDirection(Direction direction) {
        movingDirection = direction;
    }

    @Override
    public Direction getMovingDirection() {
        return movingDirection;
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
