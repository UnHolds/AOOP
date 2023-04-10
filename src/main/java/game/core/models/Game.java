package game.core.models;

import java.util.List;
import java.util.Set;

public class Game {
    private Field field;
    private List<IPlayer> players;
    private Set<IMouse> mouses;

    public Game(Field field, List<IPlayer> players, Set<IMouse> mouses) {
        this.field = field;
        this.players = players;
        this.mouses = mouses;
    }

    public Field getField() {
        return field;
    }

    public List<IPlayer> getPlayers() {
        return players;
    }

    public Set<IMouse> getMouses() {
        return mouses;
    }
}
