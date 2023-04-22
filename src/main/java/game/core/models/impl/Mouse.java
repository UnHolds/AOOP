package game.core.models.impl;

import game.core.models.Position;
import game.core.models.IMouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Mouse extends Character implements IMouse {
    public Field field;
    public Integer lastSubway;
    public Mouse(String id, Position position, String imagePath,Integer lastSubway) {
        super(id, position, imagePath);
        this.lastSubway = lastSubway;
    }

    public void setLastSubway(Integer lastSubway) {
        this.lastSubway = lastSubway;
    }

    public Integer getLastSubway(){
        return lastSubway;
    }

    @Override
    public void calculateNextMove(Position goal) {
        //Position nextGoal  = closestSubway(getPosition());
        System.out.printf("Closest exit is : x = %d, y= %d",goal.x(),goal.y());
        System.out.println();
        int mx = getPosition().x();
        int my = getPosition().y();
        int gx = goal.x();
        int gy = goal.y();
        Direction next = Direction.STOP;
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

    public Position closestSubway(Position pos, Field field){
        Map<Integer, Subway> subwayMap = field.getSubways();
        List<Position> subwayExits = new ArrayList<>();
        for (int i=0;i < subwayMap.size();i++){
            if (lastSubway == i){
                continue;
            }else {
                subwayExits.addAll(subwayMap.get(i).getExits());
            }
        }
        Position closestExit = null;
        double minDist = Double.MAX_VALUE;

        for (Position posi : subwayExits){
            double distance = Math.sqrt(Math.pow(pos.x() - posi.x(), 2) + Math.pow(pos.y() - posi.y(), 2));
            if (distance < minDist){
                minDist = distance;
                closestExit = posi;
            }
        }
        return closestExit;
    }

    @Override
    public Position searchNextExit(List<Position> sub, Position pos){
        List<Position> update = sub;
        update.remove(pos);
        Random rnd = new Random();
        Position ret = sub.get(rnd.nextInt(update.size()));
        update.add(pos);
        return ret;
    }
}
