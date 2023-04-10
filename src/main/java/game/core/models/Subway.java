package game.core.models;

import game.core.Position;
import game.core.models.IMouse;

import java.util.List;
import java.util.Set;

public class Subway {
    private List<Position> exits;
    private Set<IMouse> mouses;

    public Subway(List<Position> exits) {
        this.exits = exits;
    }

    public void mouseEnter(IMouse mouse) {
        mouses.add(mouse);
    }

    public void mouseExit(IMouse mouse) {
        mouses.remove(mouse);
    }

    public List<Position> getExits() {
        return exits;
    }
}
