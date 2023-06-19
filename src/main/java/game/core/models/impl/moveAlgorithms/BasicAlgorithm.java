package game.core.models.impl.moveAlgorithms;

import game.core.models.IMouse;
import game.core.models.IMoveAlgorithm;
import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.impl.Direction;
import game.core.models.impl.Game;
import game.core.models.impl.Subway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BasicAlgorithm implements IMoveAlgorithm {

    private IMouse mouse;

    private Integer lastSubway;
    private Integer moveAmount;
    private Subway goalSub;

    public BasicAlgorithm(IMouse mouse, Integer lastSubway, Integer moveAmount, Subway goalSub){
        this.mouse = mouse;
        this.lastSubway = lastSubway;
        this.moveAmount = moveAmount;
        this.goalSub = goalSub;
    }

    private void setLastSubway(Integer lastSubway) {
        this.lastSubway = lastSubway;
    }

    private Integer getLastSubway(){
        return lastSubway;
    }

    private Subway getGoalSub(){return goalSub;}

    public void adjustDistance(){
        if(this.moveAmount == 0){
            this.moveAmount = 0;
        } else {
            this.moveAmount = getMoveAmount() - 1;
        }
    }

    public Integer getMoveAmount(){return this.moveAmount;}

    @Override
    public void move() {
        //todo fix this
        calculateNextMove(new Position(10,10));
    }

    public void calculateNextMove(Position goal) {
        int mx = Math.round(this.mouse.getPosition().x());
        int my = Math.round(this.mouse.getPosition().y());
        int gx = Math.round(goal.x());
        int gy = Math.round(goal.y());
        if(this.mouse.getPosition() != goal) {
            if (Math.abs(mx - gx) != 0 || Math.abs(my - gy) != 0) {
                if (Math.abs(mx - gx) < Math.abs(my - gy) && Math.abs(mx - gx) != 0) {
                    //Move RIGHT
                    if (mx < gx) {
                        this.mouse.addMovingDirection(Direction.STOP);
                        this.mouse.addMovingDirection(Direction.RIGHT);
                    }
                    //Move LEFT
                    if (mx > gx) {
                        this.mouse.addMovingDirection(Direction.STOP);
                        this.mouse.addMovingDirection(Direction.LEFT);
                    }
                } else if (Math.abs(mx - gx) == Math.abs(my - gy)) {
                    //Move RIGHT
                    if (mx < gx) {
                        this.mouse.addMovingDirection(Direction.STOP);
                        this.mouse.addMovingDirection(Direction.RIGHT);
                    }
                    //Move LEFT
                    if (mx > gx) {
                        this.mouse.addMovingDirection(Direction.STOP);
                        this.mouse.addMovingDirection(Direction.LEFT);
                    }
                } else {
                    //Move DOWN
                    if (my < gy) {
                        this.mouse.addMovingDirection(Direction.STOP);
                        this.mouse.addMovingDirection(Direction.DOWN);
                    }
                    //Move UP
                    if (my > gy) {
                        this.mouse.addMovingDirection(Direction.STOP);
                        this.mouse.addMovingDirection(Direction.UP);
                    }
                    //Move RIGHT
                    if (mx < gx) {
                        this.mouse.addMovingDirection(Direction.STOP);
                        this.mouse.addMovingDirection(Direction.RIGHT);
                    }
                    //Move LEFT
                    if (mx > gx) {
                        this.mouse.addMovingDirection(Direction.STOP);
                        this.mouse.addMovingDirection(Direction.LEFT);
                    }
                }
            } else if (Math.abs(mx - gx) == 0 && Math.abs(my - gy) != 0) {
                if (my < gy) {
                    this.mouse.addMovingDirection(Direction.STOP);
                    this.mouse.addMovingDirection(Direction.DOWN);
                }
                //Move UP
                if (my > gy) {
                    this.mouse.addMovingDirection(Direction.STOP);
                    this.mouse.addMovingDirection(Direction.UP);
                }
            } else if (Math.abs(my - gy) == 0 && Math.abs(mx - gx) != 0) {
                //Move RIGHT
                if (mx < gx) {
                    this.mouse.addMovingDirection(Direction.STOP);
                    this.mouse.addMovingDirection(Direction.RIGHT);
                }
                //Move LEFT
                if (mx > gx) {
                    this.mouse.addMovingDirection(Direction.STOP);
                    this.mouse.addMovingDirection(Direction.LEFT);
                }
            } else if (Math.abs(my - gy) == 0 && Math.abs(mx - gx) == 0){
                this.mouse.addMovingDirection(Direction.STOP);
            }
        }
    }

    public Position closestSubway(Position pos, Game game){
        Map<Integer, Subway> subwayMap = game.getField().getSubways();
        List<Position> subwayExits = new ArrayList<>();
        if(getMoveAmount() != 0) {
            for (int i = 0; i < subwayMap.size(); i++) {
                if (getLastSubway() == i) {
                    continue;
                } else {
                    subwayExits.addAll(subwayMap.get(i).getExits());
                }
            }
        } else {
            subwayExits.addAll(getGoalSub().getExits());
        }
        Position closestExit = null;
        double minDist = Double.MAX_VALUE;
        List<IPlayer> cats = game.getPlayers();
        List<Position> catsPos = new ArrayList<>();
        for (IPlayer player : cats){
            catsPos.add(player.getPosition());
        }
        List<Position> saveEx = saveEntranceExit(subwayExits, catsPos);
        for (Position posi : saveEx){
            double distance = Math.sqrt(Math.pow(pos.x() - posi.x(), 2) + Math.pow(pos.y() - posi.y(), 2));
            if (distance < minDist){
                minDist = distance;
                closestExit = posi;
            }
        }
        return closestExit;
    }

    private List<Position> saveEntranceExit(List<Position> subwayExits, List<Position> catsPos){
        List<Position> saveExit = new ArrayList<>();
        saveExit.addAll(subwayExits);
        for (Position posi : subwayExits){
            for (Position cpos : catsPos){
                double catDistToSub = Math.sqrt(Math.pow(posi.x() - cpos.x(), 2) + Math.pow(posi.y() - cpos.y(), 2));
                if (catDistToSub < 2.0){
                    saveExit.remove(posi);
                }
            }
        }
        return  saveExit;
    }

    public Position searchNextExit(Subway sub, Position pos){
        /*
        //List<IPlayer> cats = sub.getSnapMap();
        List<Position> catsPos = new ArrayList<>();
        for (IPlayer player : cats){
            catsPos.add(player.getPosition());
        }
        List<Position> update = saveEntranceExit(sub.getExits(),catsPos);
        update.remove(pos);
        Position ret = pos;
        if (update.size() > 0) {
            Random rnd = new Random();
            ret = update.get(rnd.nextInt(update.size()));
        }
      return ret;
         */
        return null;
    }
}
