package game.core.models.impl;

import game.core.models.Position;
import game.core.models.IMouse;

public class Mouse extends Character implements IMouse {
    public Mouse(Position position, String imagePath) {
        super(position, imagePath);
    }
}
