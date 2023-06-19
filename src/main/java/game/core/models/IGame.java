package game.core.models;

import java.util.List;
import java.util.Set;

public interface IGame {
    Field getField();

    List<IPlayer> getPlayers();

    Set<IMouse> getMouses();
}
