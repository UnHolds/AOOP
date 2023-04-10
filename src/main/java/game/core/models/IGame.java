package game.core.models;

import game.core.models.impl.Field;

import java.util.List;
import java.util.Set;

public interface IGame {
    Field getField();

    List<IPlayer> getPlayers();

    Set<IMouse> getMouses();
}
