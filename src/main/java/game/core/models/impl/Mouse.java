package game.core.models.impl;

import game.core.models.Position;
import game.core.models.IMouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mouse extends Character implements IMouse {
    public Field field;
    public Mouse(String id, Position position, String imagePath, Field field) {
        super(id, position, imagePath);
        this.field = field;
    }

    @Override
    public void calculateNextMove() {
        Position nextGoal  = closestSubway(getPosition());
        System.out.printf("Closest exit is : x = %d, y= %d",nextGoal.x(),nextGoal.y());
        int mx = getPosition().x();
        int my = getPosition().y();
        int gx = nextGoal.x();
        int gy = nextGoal.y();
        if (Math.abs(mx - gx) != 0 && Math.abs(my - gy) != 0) {
            if (Math.abs(mx - gx) < Math.abs(my - gy)) {
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
            }
        } else if (Math.abs(mx - gx) == 0){
            if (my < gy) {
                setMovingDirection(Direction.DOWN);
            }
            //Move UP
            if (my > gy) {
                setMovingDirection(Direction.UP);
            }
        } else if (Math.abs(my - gy) == 0){
            //Move RIGHT
            if (mx < gx) {
                setMovingDirection(Direction.RIGHT);
            }
            //Move LEFT
            if (mx > gx) {
                setMovingDirection(Direction.LEFT);
            }
        }
    }

    public Position closestSubway(Position pos){
        Map<Integer, Subway> subwayMap = field.getSubways();
        List<Position> subwayExits = new ArrayList<>();
        for (int i=0;i < subwayMap.size();i++){
            subwayExits.addAll(subwayMap.get(i).getExits());
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
}
