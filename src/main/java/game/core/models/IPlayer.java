package game.core.models;

import java.awt.*;

public interface IPlayer extends ICharacter {

    int getScore();

    int getGameUIId();

    String getName();
}
