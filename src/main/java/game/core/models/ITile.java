package game.core.models;

import game.ui.ITexture;

public interface ITile {

    void checkForCharacterCollision();

    ITexture getTexture();

}
