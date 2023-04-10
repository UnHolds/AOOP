package game.core.models;

import game.core.Position;

public class Mouse extends Character implements IMouse {
    public Mouse(Position position, String imagePath) {
        super(position, imagePath);
    }
}
