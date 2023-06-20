package game.core.models.impl;

import game.core.models.IExit;
import game.core.models.IMouse;
import game.core.models.IPosition;
import game.core.models.ISubway;

import java.util.ArrayList;
import java.util.List;

public class Subway implements ISubway {


    private List<IExit> exits;
    private List<IMouse> mice;

    private List<IPosition> catPositions = new ArrayList<>();

    private boolean goal;

    public Subway(List<IExit> exits){
        this.exits = exits;
        this.mice = new ArrayList<>();
        this.goal = false;
    }

    @Override
    public List<IExit> getExits() {
        return this.exits;
    }

    @Override
    public boolean isGoal() {
        return this.goal;
    }

    @Override
    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    @Override
    public List<IMouse> getMice() {
        return this.mice;
    }

    @Override
    public void enter(IMouse mouse) {
        this.mice.add(mouse);
    }

    @Override
    public void exits(IMouse mouse) {
        this.mice.remove(mouse);
    }

    @Override
    public void setCatPositions(List<IPosition> catPositions) {
        this.catPositions = catPositions;
    }

    @Override
    public List<IPosition> getCatPositions() {
        return this.catPositions;
    }
}
