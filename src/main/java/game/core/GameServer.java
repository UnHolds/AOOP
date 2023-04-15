package game.core;

import game.controller.GameController;
import game.core.models.ICharacter;
import game.core.models.IGame;
import game.core.models.IPlayer;
import game.core.models.Position;
import game.core.models.impl.Field;
import game.core.models.impl.Game;
import game.core.models.impl.Player;
import game.core.models.impl.Subway;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameServer {
    private static final int TICK_PAUSE_MILLIS = 25;

    private IGame game;

    private GameController gameController;

    public GameServer() {
        Field field = new Field(12, 18, Set.of(
                new Subway(List.of(new Position(3, 4), new Position(7, 4))))
        );

        List<IPlayer> playerList = new ArrayList<>();
        playerList.add(new Player(null, 3, "Alice", new Position(1, 2), "cat1.png"));
        playerList.add(new Player(null, 1, "Bob", new Position(1, 3), "cat2.png"));
        playerList.add(new Player(null, 2, "Bob", new Position(1, 4), "cat3.png"));
        playerList.add(new Player(null,4, "Eve", new Position(1, 5), "cat4.png"));

        game = new Game(field, playerList, Set.of());
    }

    public void run() {
        while (true) {
            for(ICharacter character : game.getMouses()) {
                character.move();
            }

            for(ICharacter character : game.getPlayers()) {
                character.move();
            }

            gameController.updateGameWindow();

            try {
                Thread.sleep(TICK_PAUSE_MILLIS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public IGame getGame() {
        return game;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
}
