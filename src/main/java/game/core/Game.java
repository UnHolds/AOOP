package game.core;


import game.core.models.Edge;
import game.core.models.Mouse;
import game.core.models.SubwayMap;
import game.ui.GameWindow;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class Game implements IGame{
    @Override
    public void showStartScreen() {
        // TODO implement
    }

    @Override
    public void establishNetworkConnection() {
        // TODO implement
    }

    @Override
    public void initializeGameUI() {
        // TODO implement
    }

    @Override
    public void initializeGameLogic() {
        //Initialize Subway Map
        List<Edge> edges = Arrays.asList(
                new Edge(0, 1, 0, true), new Edge(0, 2,0, true), new Edge(0, 3,0, true), new Edge(0, 4,0, true),
                new Edge(1, 2,0, true), new Edge(1, 3,0, true), new Edge(1, 4,0, true),
                new Edge(2, 3,0, true), new Edge(2, 4,0, true),
                new Edge(3, 4,0, true),
                new Edge(0,5,3, true), new Edge(0,6,5, true), new Edge(0,7,4, true),new Edge(0,8,2, true), new Edge(0,9,1, true),
                new Edge(1,5,3, true), new Edge(1,6,5, true), new Edge(1,7,4, true),new Edge(1,8,2, true), new Edge(1,9,1, true),
                new Edge(2,5,3, true), new Edge(2,6,5, true), new Edge(2,7,4, true),new Edge(2,8,2, true), new Edge(2,9,1, true),
                new Edge(3,5,3, true), new Edge(3,6,5, true), new Edge(3,7,4, true),new Edge(3,8,2, true), new Edge(3,9,1, true),
                new Edge(4,5,3, true), new Edge(4,6,5, true), new Edge(4,7,4, true),new Edge(4,8,2, true), new Edge(4,9,1, true),
                new Edge(0,10,3, true), new Edge(0,11,5, true), new Edge(0,12,4, true),new Edge(0,13,2, true), new Edge(0,14,1, true),
                new Edge(1,10,3, true), new Edge(1,11,5, true), new Edge(1,12,4, true),new Edge(1,13,2, true), new Edge(1,14,1, true),
                new Edge(2,10,3, true), new Edge(2,11,5, true), new Edge(2,12,4, true),new Edge(2,13,2, true), new Edge(2,14,1, true),
                new Edge(3,10,3, true), new Edge(3,11,5, true), new Edge(3,12,4, true),new Edge(3,13,2, true), new Edge(3,14,1, true),
                new Edge(4,10,3, true), new Edge(4,11,5, true), new Edge(4,12,4, true),new Edge(4,13,2, true), new Edge(4,14,1, true),
                new Edge(5, 6, 0, true), new Edge(5, 7,0, true), new Edge(5, 8,0, true), new Edge(5, 9,0, true),
                new Edge(6, 7,0, true), new Edge(6, 8,0, true), new Edge(6, 9,0, true),
                new Edge(7, 8,0, true), new Edge(7, 9,0, true),
                new Edge(8, 9,0, true),
                new Edge(5,10,3, true), new Edge(5,11,5, true), new Edge(5,12,4, true),new Edge(5,13,2, true), new Edge(5,14,1, true),
                new Edge(6,10,3, true), new Edge(6,11,5, true), new Edge(6,12,4, true),new Edge(6,13,2, true), new Edge(6,14,1, true),
                new Edge(7,10,3, true), new Edge(7,11,5, true), new Edge(7,12,4, true),new Edge(7,13,2, true), new Edge(7,14,1, true),
                new Edge(8,10,3, true), new Edge(8,11,5, true), new Edge(8,12,4, true),new Edge(8,13,2, true), new Edge(8,14,1, true),
                new Edge(9,10,3, true), new Edge(9,11,5, true), new Edge(9,12,4, true),new Edge(9,13,2, true), new Edge(9,14,1, true),
                new Edge(10, 11, 0, true), new Edge(10, 12,0, true), new Edge(10, 13,0, true), new Edge(10, 14,0, true),
                new Edge(11, 12,0, true), new Edge(11, 13,0, true), new Edge(11, 14,0, true),
                new Edge(12, 13,0, true), new Edge(12, 14,0, true),
                new Edge(13, 14,0, true)
        );

        // konstruiere einen Graphen aus der gegebenen Kantenliste
        SubwayMap subwayMap = new SubwayMap(edges);

        // Adjazenzlistendarstellung des Graphen drucken
        SubwayMap.printMap(subwayMap);
        Mouse mouse1 = new Mouse(true, subwayMap, false, 10,0,0,50);
        mouse1.calculateNextMove();
        }

    @Override
    public void showWinningScreen() {

    }

    public Dimension getPreferredSize() {
        return new Dimension(400, 400);
    }

    @Override
    public void deconnectFromNetwork() {

    }

    public static void main(String[] args){
        System.out.println("Please call the other methods here when implemented");

        GameWindow gameWindow = new GameWindow();
        gameWindow.initWindow();
    }
}
