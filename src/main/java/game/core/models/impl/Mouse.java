package game.core.models.impl;

import game.core.models.Position;
import game.core.models.IMouse;

public class Mouse extends Character implements IMouse {
    public Mouse(String id, Position position, String imagePath) {
        super(id, position, imagePath);
    }
    public Mouse(Position position, String imagePath) {
        super(position, imagePath);
    }
}
