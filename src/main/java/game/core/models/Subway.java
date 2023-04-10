package game.core.models;

import game.core.Position;

import java.util.Set;

public class Subway {
    private Set<Position> exits;
    private Set<IMouse> mouses;

    public Subway(Set<Position> exits) {
        this.exits = exits;
    }

    public void mouseEnter(IMouse mouse) {
        mouses.add(mouse);
    }

    public void mouseExit(IMouse mouse) {
        mouses.remove(mouse);
    }

    public Set<Position> getExits() {
        return exits;
    }
}
