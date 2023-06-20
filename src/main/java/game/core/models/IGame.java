package game.core.models;

import java.util.List;
import java.util.Set;

public interface IGame {
    IField getField();

    List<IPlayer> getPlayers();

    List<IMouse> getMouses();
}
