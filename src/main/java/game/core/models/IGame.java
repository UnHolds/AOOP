package game.core.models;

import java.util.List;
import java.util.Set;

public interface IGame {
    IField getField();

    void setMice(List<IMouse> mice);

    List<IPlayer> getPlayers();

    Set<IMouse> getMouses();
}
