package game.core.models.impl.moveAlgorithms;

import game.core.models.IMouse;
import game.core.models.IMoveAlgorithm;
import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.impl.Subway;

import java.util.List;

public class AvoidAlgorithm implements IMoveAlgorithm {

    private Subway residingSubway;

    private Subway goalSubway;

    private Position destination;

    private List<Subway> subways;
    private IMouse mouse;

    private long waitInSubway = 0;

    private List<IPlayer> cats;


    public AvoidAlgorithm(IMouse mouse, List<IPlayer> cats, List<Subway> subways){
        this.subways = subways;
        this.mouse = mouse;
        this.cats = cats;
    }


    public void getBestMovingDirectionToPosition(Position goalPos){
        Position mousePos = this.mouse.getPosition();

    }

    @Override
    public void move() {
        if(this.residingSubway == null){
            //move to goal.

        }else if(System.currentTimeMillis() > waitInSubway){
            //exit subway
        }else{
            //do nothing and wait in subway :)
        }
    }
}
