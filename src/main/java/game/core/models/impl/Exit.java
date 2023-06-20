package game.core.models.impl;

import game.core.models.IExit;
import game.core.models.IPosition;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Exit implements IExit {

    private Image image;
    private IPosition position;

    public Exit(IPosition position){
        this.position = position;
        try {
            this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("mouse.png")); //TODO change this to hole.png
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    @Override
    public Image getImage() {
        return this.image;
    }
}
