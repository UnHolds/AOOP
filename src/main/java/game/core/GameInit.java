package game.core;

import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.ISubway;

import java.util.ArrayList;
import java.util.List;

public class GameInit {

    private List<ISubway> subways;
    private List<IMouse> mice;

    private List<IPlayer> players;

    private int rowCount = 18;
    private int colCount = 25;

    public GameInit() {
        subways = new ArrayList<>();
        mice = new ArrayList<>();
    }


    public List<ISubway> getSubways() {
        return this.subways;
    }

    public List<IMouse> getMice() {
        return this.mice;
    }

    public int getRowCount(){
        return this.rowCount;
    }

    public int getColCount(){
        return this.colCount;
    }

    public void setPlayers(List<IPlayer> players){
        this.players = players;
    }

    public List<IPlayer> getPlayers(){
        return this.players;
    }

}
