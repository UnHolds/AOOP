package game.core;

import game.controller.GameController;
import game.core.models.*;
import game.core.models.impl.Field;
import game.core.models.impl.Game;
import game.core.models.impl.Player;
import game.core.models.impl.Subway;

import java.util.*;

public class GameServer {
    private static final int TICK_PAUSE_MILLIS = 25;

    private IGame game;

    private GameController gameController;

    public GameServer() {
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
        Field field = new Field(12, 18, subways);

        List<IPlayer> playerList = new ArrayList<>();
        playerList.add(new Player(null, 3, "Alice", new Position(1, 2), "cat1.png"));
        playerList.add(new Player(null, 1, "Bob", new Position(1, 3), "cat2.png"));
        playerList.add(new Player(null, 2, "Bob", new Position(1, 4), "cat3.png"));
        playerList.add(new Player(null,4, "Eve", new Position(1, 5), "cat4.png"));

        game = new Game(field, playerList, Set.of());
    }

    public void run() {
        while (true) {
            for(IMouse mouse : game.getMouses()) {
                Position goal = mouse.closestSubway(mouse.getPosition(), (Game) game);
                mouse.calculateNextMove(goal);
                mouse.move();
                if (mouse.getPosition().x() == goal.x() && mouse.getPosition().y() == goal.y()) {
                    for (int i = 0; i < game.getField().getSubways().size(); i++) {
                        List<Position> sub = game.getField().getSubways().get(i).getExits();
                        if (sub.contains(mouse.getPosition())) {
                            game.getField().getSubways().get(i).mouseEnter(mouse,game.getPlayers());
                            mouse.setLastSubway(i);
                        }
                    }
                    break;
                }
                for (int i = 0; i < game.getField().getSubways().size(); i++) {
                    Subway s = game.getField().getSubways().get(i);
                    Set<IMouse> um = s.getMouses();
                    if (um.contains(mouse)) {
                        Position np = mouse.searchNextExit(game.getField().getSubways().get(i), mouse.getPosition());
                        game.getField().getSubways().get(i).mouseExit(mouse);
                        mouse.setPosition(np);
                        mouse.setLastSubway(i);
                        break;
                    }
                }
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
