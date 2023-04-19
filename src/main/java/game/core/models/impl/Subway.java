package game.core.models.impl;

import game.core.models.Position;
import game.core.models.IMouse;

import java.util.*;

public class Subway {
    private List<Position> exits;
    private Set<IMouse> mouses = new HashSet<>();

    public Subway(List<Position> exits) {
        this.exits = exits;
    }

    public void mouseEnter(IMouse mouse) {
        mouses.add(mouse);
    }

    public void mouseExit(IMouse mouse) {
        mouses.remove(mouse);
    }

    public Set<IMouse> getMouses() {
        return mouses;
    }

    public List<Position> getExits() {
        return exits;
    }
}
