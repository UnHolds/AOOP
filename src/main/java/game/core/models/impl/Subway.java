package game.core.models.impl;

import game.core.models.IExit;
import game.core.models.IMouse;
import game.core.models.ISubway;

import java.util.ArrayList;
import java.util.List;

public class Subway implements ISubway {


    private List<IExit> exits;
    private List<IMouse> mice;

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
}
