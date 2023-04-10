package game.core;


import game.core.models.Field;
import game.core.models.Subway;
import game.ui.GameWindow;

import java.util.Set;

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
        // TODO implement
    }

    @Override
    public void showWinningScreen() {

    }

    @Override
    public void deconnectFromNetwork() {

    }

    public static void main(String[] args){
        System.out.println("Please call the other methods here when implemented");

        Field field = new Field(12, 18, Set.of(
                new Subway(Set.of(new Position(3, 4))))
        );
        GameWindow gameWindow = new GameWindow(field);
        gameWindow.initWindow();
    }
}
