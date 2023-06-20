package game.core.models;

import java.util.List;

public interface ISubway {

    List<IExit> getExits();

    boolean isGoal();

    void setGoal(boolean goal);

    List<IMouse> getMice();

    void enter(IMouse mouse);

    void exits(IMouse mouse);

}
