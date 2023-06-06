package game.core.models.impl;

import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.IMouse;

import java.util.*;

public class Subway {
    private List<Position> exits;
    private Set<IMouse> mouses = new HashSet<>();
    private List<IPlayer> snapMap;

    public Subway(List<Position> exits) {
        this.exits = exits;
    }

    public void mouseEnter(IMouse mouse, List<IPlayer> cats) {
        mouses.add(mouse);
        setSnapMap(cats);
    }

    public void mouseExit(IMouse mouse) {
        mouses.remove(mouse);
    }

    private void setSnapMap(List<IPlayer> cats){
        snapMap = cats;
    }

    public List<IPlayer>  getSnapMap(){
        return snapMap;
    }

    public Set<IMouse> getMouses() {
        return mouses;
    }

    public List<Position> getExits() {
        return exits;
    }
}
