package game.core.models.impl;

import game.core.models.Position;
import game.core.models.ICharacter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Character implements ICharacter {
    private Position position;
    private Image image;

    public Character(Position position, String imagePath) {
        this.position = position;
        this.image = loadImageFromResources(imagePath);
    }

    public void move(Direction direction) {
        position = position.move(direction);
    }

    @Override
    public Image getImage() {
        return image;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String getId() {
        return null;
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
