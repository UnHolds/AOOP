package game;

import game.core.models.IMouse;
import game.core.models.IPlayer;
import game.core.models.impl.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import game.core.models.Position;

import java.util.*;

public class FieldTest {
    public FieldTest(){}

    @Test
    @DisplayName("Test Field")
    public void testField() {
        Position pos1 = new Position(1, 2);
        Position pos2 = new Position(3, 4);
        Position pos3 = new Position(5, 6);
        Position pos4 = new Position(7, 8);
        // SUBWAY 1
        Position pos5 = new Position(1, 1);
        Position pos6 = new Position(5, 5);
        Position pos7 = new Position(9, 9);
        List<Position> sub1 = new ArrayList<>();
        sub1.add(pos5);
        sub1.add(pos6);
        sub1.add(pos7);
        // SUBWAY 2
        Position pos8 = new Position(9, 1);
        Position pos9 = new Position(3, 3);
        Position pos10 = new Position(1, 9);
        List<Position> sub2 = new ArrayList<>();
        sub2.add(pos8);
        sub2.add(pos9);
        sub2.add(pos10);

        Subway subway1 = new Subway(sub1);
        Subway subway2 = new Subway(sub2);
        Map<Integer, Subway> subways = new HashMap<>();
        subways.put(0, subway1);
        subways.put(1, subway2);

        Field field = new Field(10, 10, subways);
        IMouse mouse2 = new Mouse("ID_MOUSE_2", pos4, "mouse.png", field,-1);
        IMouse mouse1 = new Mouse("ID_MOUSE_1", pos3, "mouse.png", field,-1);

        for (int i = 0; i < field.getColumnCount();i++){
            for (int j = 0; j < field.getRowCount();j++){
                if (j < field.getColumnCount()){
                    Position test = new Position(i,j);
                    List<Position> exits = new ArrayList<>();
                    Map<Integer, Subway> subwayMap = field.getSubways();
                    int subs = subwayMap.size();
                    for(int x=0; x < subs;x++) {
                        exits.addAll(subwayMap.get(x).getExits());
                    }
                    if (exits.contains(test)){
                        System.out.printf("|U");
                    } else if (mouse1.getPosition().y() == test.y() && mouse2.getPosition().x() == test.x()){
                        System.out.printf("|M");
                    }else if (mouse2.getPosition().y() == test.y() && mouse2.getPosition().x() == test.x()){
                        System.out.printf("|M");
                    } else {
                        System.out.printf("| ");
                    }
                }
                if (j + 1 >= field.getColumnCount()){
                    System.out.printf("|\n");

                    System.out.printf("---------------------\n");
                }
            }
        }

        System.out.printf("ROW COUNT: %d",field.getRowCount());
        System.out.println();
        System.out.printf("COLUMN COUNT : %d", field.getColumnCount());
        System.out.println();

        Map<Integer, Subway> subwayMap = field.getSubways();
        int subsAmount = subwayMap.size();
        for (int y = 0; y < subsAmount;y++){
            List<Position> te = field.getSubways().get(y).getExits();
            for ( Position p : te){
                System.out.printf("Subway%d : %d,%d \n",y, p.y(), p.x());
            }
        }
        for(int a = 0; a < 50; a++) {
            Position goal = mouse1.closestSubway(mouse1.getPosition());
            while (mouse1.getPosition() != goal) {
                System.out.printf("OLD Position of m1: x = %d, y = %d", mouse1.getPosition().x(), mouse1.getPosition().y());
                System.out.println();
                mouse1.calculateNextMove(goal);
                mouse1.move();
                System.out.printf("New Position of m1: x = %d, y = %d", mouse1.getPosition().x(), mouse1.getPosition().y());
                System.out.println();
                if (mouse1.getPosition().x() == goal.x() && mouse1.getPosition().y() == goal.y()) {
                    for (int i = 0; i < field.getSubways().size(); i++) {
                        List<Position> sub = field.getSubways().get(i).getExits();
                        if (sub.contains(mouse1.getPosition())) {
                            field.getSubways().get(i).mouseEnter(mouse1);
                        }
                    }
                    break;
                }
            }
            for (int i = 0; i < field.getSubways().size(); i++) {
                Subway s = field.getSubways().get(i);
                Set<IMouse> um = s.getMouses();
                System.out.printf("TEST : %b", um.contains(mouse1));
                System.out.println();
                System.out.printf("SIZE OF S : %d",field.getSubways().get(i).getExits().size());
                if (um.contains(mouse1)) {
                    Position np = mouse1.searchNextExit(field.getSubways().get(i).getExits(), mouse1.getPosition());
                    //System.out.printf("NEW EXIT: x=%d y=%d", np.x(), np.y());
                    System.out.println();
                    field.getSubways().get(i).mouseExit(mouse1);
                    mouse1.setPosition(np);
                    mouse1.setLastSubway(i);
                    break;
                }
            }

            System.out.printf("New Position of m1: x = %d, y = %d", mouse1.getPosition().x(), mouse1.getPosition().y());
            System.out.println();
        }
    }
}
