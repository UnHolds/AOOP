package game.core;

import game.core.models.*;
import game.core.models.impl.Exit;
import game.core.models.impl.Position;
import game.core.models.impl.Subway;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameInit {

    private List<ISubway> subways;
    private List<IMouse> mice;

    private List<IPlayer> players;

    private int rowCount = 18;
    private int colCount = 25;

    private int numberOfSubways = 2; //needs to be at least 2

    private int numberOfMice = 5;


    public GameInit() {
        generateSubways();
        generateMice();
    }

    private IPosition getRandomPositionInGameField(){
        Random rand = new Random();
        int x = rand.nextInt(colCount);
        int y = rand.nextInt(rowCount);

        return new Position(x,y);
    }

    private void generateSubways(){
        Random rand = new Random();
        this.subways = new ArrayList<>();

        for(int i = 0; i < numberOfSubways; i++){

            int numberOfExits = 2 + rand.nextInt(3);
            List<IExit> exits = new ArrayList<>();

            for(int j = 0; j < numberOfExits; j++){
                exits.add(new Exit(getRandomPositionInGameField()));
            }

            Subway subway = new Subway(exits);

            if(i == 0){
                subway.setGoal(true);
            }else{
                subway.setGoal(false);
            }

            this.subways.add(subway);
        }
    }


    private void generateMice(){
        this.mice = new ArrayList<>();
        //TODO create mice
    }


    public List<ISubway> getSubways() {
        return this.subways;
    }

    public List<IMouse> getMice() {
        return this.mice;
    }

    public int getRowCount(){
        return this.rowCount;
    }

    public int getColCount(){
        return this.colCount;
    }

    public void setPlayers(List<IPlayer> players){
        this.players = players;
    }

    public List<IPlayer> getPlayers(){
        return this.players;
    }

}
