package game.core.models.impl;

import game.core.models.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Game implements IGame {

    private List<IPlayer> players;
    private List<IMouse> mice;

    private IField field;

    public Game(List<IPlayer> players, List<IMouse> mice, List<ISubway> subways, int rowCount, int colCount){
        this.players = players;
        this.mice = mice;
        this.field = new Field(rowCount, colCount, subways);
    }

    @Override
    public IField getField() {
        return this.field;
    }


    @Override
    public List<IPlayer> getPlayers() {
        return this.players.stream().sorted(Comparator.comparing(p -> p.getScore())).collect(Collectors.toList());
    }

    @Override
    public List<IMouse> getMice() {
        return this.mice;
    }
}
