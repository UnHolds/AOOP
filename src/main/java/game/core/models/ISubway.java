package game.core.models;

import java.util.List;

public interface ISubway {

    /**
     * PRE: ISubway is not null
     * POST: List of exits if they have been initialized or null otherwise
     *
     * @return List of IExit objects or null
     */
    List<IExit> getExits();

    /**
     * PRE: ISubway is not null
     * POST: Returns if this subway is a goal subway as boolean
     *
     * @return if this subway is a goal subway as boolean
     */
    boolean isGoal();

    /**
     * PRE: ISubway is not null, goal is a boolean
     * POST: Sets the isGoal value of this subway
     *
     * @param goal boolean if subway is a goal or not
     */
    void setGoal(boolean goal);

    /**
     * PRE: ISubway is not null
     * POST: List of mice if they have been initialized or null otherwise
     *
     * @return
     */
    List<IMouse> getMice();

    /**
     * PRE: ISubway is not null, mouse is not null
     * POST: Adds the mouse to the mouse list of the subway
     *
     * @param mouse
     */
    void enter(IMouse mouse);

    /**
     * PRE: ISubway is not null, mouse is not null
     * POST: Removes the mouse from the mouse list of the subway or does nothing if mouse is not in this subway
     *
     * @param mouse
     */
    void exits(IMouse mouse);

    /**
     * PRE: ISubway is not null, catPositions is not null
     * POST: Sets the catPositions that the mice in the subway know of
     *
     * @param catPositions positions of cats on the surface
     */
    void setCatPositions(List<IPosition> catPositions);

    /**
     * PRE: ISubway is not null
     * POST: Returns a list of cat positions that the mice in the subway know of or null
     *
     * @return list of cat positions that the mice in the subway know of or null
     */
    List<IPosition> getCatPositions();

}
