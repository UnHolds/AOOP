package game.core.models;

import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Mouse implements IMouse{

    private boolean alive;
    private SubwayMap map;
    private boolean won;
    private int goal;
    private int currentPos;
    private int nextExit;
    private int distance;
    private int minDistance;



    public Mouse(boolean alive, SubwayMap map, boolean won, int goal, int currentPos, int nextExit, int distance, int minDistance){
        this.alive = alive;
        this.map = map;
        this.won = won;
        this.goal = goal;
        this.currentPos = currentPos;
        this.nextExit = nextExit;
        this.distance = distance;
        this.minDistance = minDistance;
    }

    public boolean isAlive() {
        return alive;
    }

    public SubwayMap getMap() {
        return map;
    }

    public boolean isWon() {
        return won;
    }

    public int getGoal() {
        return goal;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public int getNextExit() {
        return nextExit;
    }

    public int getDistance() {
        return distance;
    }

    public int getMinDistance() {
        return minDistance;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setMap(SubwayMap map) {
        this.map = map;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public void setNextExit(int nextExit) {
        this.nextExit = nextExit;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance = minDistance;
    }

    @Override
    public void move() {

    }

    @Override
    public void deathAnimation() {

    }

    @Override
    public void calculateNextMove(){
        if (distance < minDistance){
            List<Integer> directNeighbor = map.getStationExit(currentPos);
            Random rand = new Random();
            int nextExit = directNeighbor.get(rand.nextInt(directNeighbor.size()));
            setNextExit(nextExit);
            List<Station> nextSubway = map.getSubways(currentPos);
            Station next  = nextSubway.get(rand.nextInt(nextSubway.size()));
            setCurrentPos(next.getValue());
            distance += next.getWeight();
            System.out.println();
        }
        //Run towards the win
        List<Integer> goalStation = map.getStationExit(goal);
        if (goalStation.contains(currentPos) && distance >= minDistance){
            setWon(true);
        }
        if (!goalStation.contains(currentPos) && distance >= minDistance){
            List<Integer> directNeighbor = map.getStationExit(currentPos);
            Random rand = new Random();
            int nextExit = directNeighbor.get(rand.nextInt(directNeighbor.size()));
            setCurrentPos(nextExit);
            Station lastStop = map.getShortestMove(currentPos, goalStation);
            try {
                Thread.sleep((long) (0.1 * lastStop.weight));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setCurrentPos(lastStop.getValue());
            distance += lastStop.getWeight();
            setWon(true);
            System.out.printf("Reached Goal, after distance %d on Node %d", distance, currentPos);
            System.out.println();
        }

    }

    @Override
    public void broadCastCatLocationsToMiceNearby() {

    }
}
