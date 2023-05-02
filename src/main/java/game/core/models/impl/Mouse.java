package game.core.models.impl;

import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.IMouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Mouse extends Character implements IMouse {
    public Integer lastSubway;
    public Integer moveAmount;
    public Subway goalSub;
    public Mouse(String id, Position position, String imagePath,Integer lastSubway, Integer moveAmount, Subway goalSub) {
        super(id, position, imagePath);
        this.lastSubway = lastSubway;
        this.moveAmount = moveAmount;
        this.goalSub = goalSub;
    }

    public void setLastSubway(Integer lastSubway) {
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
    public void calculateNextMove(Position goal) {
        System.out.printf("Closest exit is : x = %d, y= %d",goal.x(),goal.y());
        System.out.println();
        int mx = getPosition().x();
        int my = getPosition().y();
        int gx = goal.x();
        int gy = goal.y();
        if(getPosition() != goal) {
            if (Math.abs(mx - gx) != 0 || Math.abs(my - gy) != 0) {
                System.out.println();
                if (Math.abs(mx - gx) < Math.abs(my - gy) && Math.abs(mx - gx) != 0) {
                    //Move RIGHT
                    if (mx < gx) {
                        setMovingDirection(Direction.RIGHT);
                    }
                    //Move LEFT
                    if (mx > gx) {
                        setMovingDirection(Direction.LEFT);
                    }
                } else if (Math.abs(mx - gx) == Math.abs(my - gy)) {
                    //Move RIGHT
                    if (mx < gx) {
                        setMovingDirection(Direction.RIGHT);
                    }
                    //Move LEFT
                    if (mx > gx) {
                        setMovingDirection(Direction.LEFT);
                    }
                } else {
                    //Move DOWN
                    if (my < gy) {
                        setMovingDirection(Direction.DOWN);
                    }
                    //Move UP
                    if (my > gy) {
                        setMovingDirection(Direction.UP);
                    }
                    //Move RIGHT
                    if (mx < gx) {
                        setMovingDirection(Direction.RIGHT);
                    }
                    //Move LEFT
                    if (mx > gx) {
                        setMovingDirection(Direction.LEFT);
                    }
                }
            } else if (Math.abs(mx - gx) == 0 && Math.abs(my - gy) != 0) {
                if (my < gy) {
                    setMovingDirection(Direction.DOWN);
                }
                //Move UP
                if (my > gy) {
                    setMovingDirection(Direction.UP);
                }
            } else if (Math.abs(my - gy) == 0 && Math.abs(mx - gx) != 0) {
                //Move RIGHT
                if (mx < gx) {
                    setMovingDirection(Direction.RIGHT);
                }
                //Move LEFT
                if (mx > gx) {
                    setMovingDirection(Direction.LEFT);
                }
            } else if (Math.abs(my - gy) == 0 && Math.abs(mx - gx) == 0){
                setMovingDirection(Direction.STOP);
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

    @Override
    public Position searchNextExit(Subway sub, Position pos){
        List<IPlayer> cats = sub.getSnapMap();
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
    }
}
