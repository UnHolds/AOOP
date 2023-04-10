package game.controller;


import game.core.models.*;
import game.core.models.impl.Field;
import game.core.models.impl.Game;
import game.core.models.impl.Player;
import game.core.models.impl.Subway;
import game.ui.GameWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameController implements IGameController {
    private Game game;

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
        GameWindow gameWindow = new GameWindow(game);
        gameWindow.initWindow();
    }

    @Override
    public void initializeGameLogic() {
        Field field = new Field(12, 18, Set.of(
                new Subway(List.of(new Position(3, 4))))
        );

        List<IPlayer> playerList = new ArrayList<>();
        playerList.add(new Player(3, "Alice", new Position(1, 2), "cat1.png"));
        playerList.add(new Player(1, "Bob", new Position(1, 3), "cat2.png"));
        playerList.add(new Player(2, "Bob", new Position(1, 4), "cat3.png"));
        playerList.add(new Player(4, "Eve", new Position(1, 5), "cat4.png"));

        game = new Game(field, playerList, null);
    }

    @Override
    public void showWinningScreen() {

    }

    @Override
    public void deconnectFromNetwork() {

    }

    public static void main(String[] args){
        System.out.println("Please call the other methods here when implemented");
        GameController gameController = new GameController();
        gameController.initializeGameLogic();
        gameController.initializeGameUI();
    }
}
