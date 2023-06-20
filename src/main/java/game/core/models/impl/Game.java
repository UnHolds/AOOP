package game.core.models.impl;

import game.core.models.*;

import java.util.List;
import java.util.Set;

public class Game implements IGame {

    private List<IPlayer> players;
    private List<IMouse> mice;
    private List<ISubway> subways;

    public Game(List<IPlayer> players, List<IMouse> mice, List<ISubway> subways){
        this.players = players;
        this.mice = mice;
        this.subways = subways;
    }

    @Override
    public IField getField() {
        return null;
    }


    @Override
    public List<IPlayer> getPlayers() {
        return this.players;
    }

    @Override
    public List<IMouse> getMouses() {
        return this.mice;
    }
}
