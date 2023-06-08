package game.core.models.impl;

import game.core.models.IGame;
import game.core.models.IMouse;
import game.core.models.IPlayer;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class Game implements IGame {
    private Field field;
    private List<IPlayer> players;
    private Set<IMouse> mouses;

    public Game(Field field, List<IPlayer> players, Set<IMouse> mouses) {
        this.field = field;
        this.players = players;
        this.mouses = mouses;
    }


    public void setMice(Set<IMouse> mice){
        this.mouses = mice;
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

    public Subway getGoal() {
        Random rand = new Random();
        int goal = rand.nextInt(field.getSubways().size());
        return field.getSubways().get(goal);
    }
}
