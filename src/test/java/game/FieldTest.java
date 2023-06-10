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
/*
    @Test
    @DisplayName("Test Field")
    public void testField() {
        Position pos1 = new Position(3, 2);
        Position pos2 = new Position(1, 4);
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
        // SUBWAY 3 - GOAL SUBWAY
        Position pos11 = new Position(0, 9);
        Position pos12 = new Position(7, 9);
        Position pos13 = new Position(9, 0);
        List<Position> sub3 = new ArrayList<>();
        sub3.add(pos11);
        sub3.add(pos12);
        sub3.add(pos13);

        Subway subway1 = new Subway(sub1);
        Subway subway2 = new Subway(sub2);
        Subway subway3 = new Subway(sub3);
        Map<Integer, Subway> subways = new HashMap<>();
        subways.put(0, subway1);
        subways.put(1, subway2);
        subways.put(2, subway3);

        Random rand = new Random();
        int mouseMoves1 = rand.nextInt(51) + 50;
        int mouseMoves2 = rand.nextInt(51) + 50;

        Field field = new Field(10, 10, subways);
        IPlayer p1 = new Player("ID_PLAYER_1",1, "PLAYER", pos1, "cat1.png");
        IPlayer p2 = new Player("ID_PLAYER_2",1, "PLAYER2", pos2, "cat2.png");
        IMouse mouse2 = new Mouse("ID_MOUSE_2", pos4, "mouse.png", -1,mouseMoves1,subway1);
        IMouse mouse1 = new Mouse("ID_MOUSE_1", pos3, "mouse.png", -1,mouseMoves2,subway1);
        List<IPlayer> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        Set<IMouse> mouses = new HashSet<>();
        mouses.add(mouse1);
        mouses.add(mouse2);
        Game game = new Game(field, players, mouses);

        for (int i = 0; i < game.getField().getColumnCount();i++){
            for (int j = 0; j < game.getField().getRowCount();j++){
                if (j < game.getField().getColumnCount()){
                    Position test = new Position(i,j);
                    List<Position> exits = new ArrayList<>();
                    Map<Integer, Subway> subwayMap = game.getField().getSubways();
                    int subs = subwayMap.size();
                    for(int x=0; x < subs;x++) {
                        exits.addAll(subwayMap.get(x).getExits());
                    }
                    if (exits.contains(test)){
                        System.out.printf("|U ");
                    } else if (mouse1.getPosition().y() == test.y() && mouse2.getPosition().x() == test.x()){
                        System.out.printf("|M1");
                    }else if (mouse2.getPosition().y() == test.y() && mouse2.getPosition().x() == test.x()){
                        System.out.printf("|M2");
                    } else if (p1.getPosition().y() == test.y() && p1.getPosition().x() == test.x()){
                        System.out.printf("|P1");
                    }else if (p2.getPosition().y() == test.y() && p2.getPosition().x() == test.x()){
                        System.out.printf("|P2");
                    }else {
                        System.out.printf("|  ");
                    }
                }
                if (j + 1 >= game.getField().getColumnCount()){
                    System.out.printf("|\n");

                    System.out.printf("-------------------------------\n");
                }
            }
        }

        System.out.printf("ROW COUNT: %d",game.getField().getRowCount());
        System.out.println();
        System.out.printf("COLUMN COUNT : %d", game.getField().getColumnCount());
        System.out.println();

        Map<Integer, Subway> subwayMap = game.getField().getSubways();
        int subsAmount = subwayMap.size();
        for (int y = 0; y < subsAmount;y++){
            List<Position> te = game.getField().getSubways().get(y).getExits();
            for ( Position p : te){
                System.out.printf("Subway%d : %d,%d \n",y, p.y(), p.x());
            }
        }
        for(int a = 0; a < mouseMoves1 + 100; a++) {
            Position goal = mouse1.closestSubway(mouse1.getPosition(),game);
            if(mouse1.getMoveAmount() == 0 && subway1.getMouses().contains(mouse1)){
                System.out.printf("MOUSE DONE !!!!");
                break;
            }
            while (mouse1.getPosition() != goal) {
                Position goal1 = mouse1.closestSubway(mouse1.getPosition(),game);
                System.out.printf("OLD Position of m1: x = %d, y = %d", mouse1.getPosition().x(), mouse1.getPosition().y());
                System.out.println();
                mouse1.calculateNextMove(goal1);
                mouse1.move();
                mouse1.adjustDistance();
                System.out.printf("New Position of m1: x = %d, y = %d", mouse1.getPosition().x(), mouse1.getPosition().y());
                System.out.println();
                if (mouse1.getPosition().x() == goal1.x() && mouse1.getPosition().y() == goal1.y()) {
                    for (int i = 0; i < game.getField().getSubways().size(); i++) {
                        List<Position> sub = game.getField().getSubways().get(i).getExits();
                        if (sub.contains(mouse1.getPosition())) {
                            game.getField().getSubways().get(i).mouseEnter(mouse1,game.getPlayers());
                            mouse1.setLastSubway(i);
                        }
                    }
                    break;
                }
            }
            for (int i = 0; i < game.getField().getSubways().size(); i++) {
                if(mouse1.getMoveAmount() == 0 && subway1.getMouses().contains(mouse1)){
                    break;
                }
                Subway s = game.getField().getSubways().get(i);
                Set<IMouse> um = s.getMouses();
                System.out.printf("TEST : %b, MOVEMENT : %d", um.contains(mouse1), mouse1.getMoveAmount());
                System.out.println();
                if (um.contains(mouse1)) {
                    Position np = mouse1.searchNextExit(game.getField().getSubways().get(i), mouse1.getPosition());
                    //System.out.printf("NEW EXIT: x=%d y=%d", np.x(), np.y());
                    System.out.println();
                    game.getField().getSubways().get(i).mouseExit(mouse1);
                    mouse1.setPosition(np);
                    mouse1.setLastSubway(i);
                    break;
                }
            }
        }


    }
     */

}
