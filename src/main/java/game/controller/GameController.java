package game.controller;


import game.core.GameClient;
import game.core.GameServer;
import game.core.handler.CharacterMovementController;
import game.ui.GameWindow;

public class GameController implements IGameController {
    private GameWindow gameWindow;
    private GameClient gameClient;

    public GameController(GameClient gameClient) {
        //gameServer.setGameController(this);
        gameClient.setController(this);
        this.gameClient = gameClient;
    }

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
        gameWindow = new GameWindow(gameClient.getGame());
        gameWindow.initWindow();
        gameWindow.showGameFieldPanel();
        CharacterMovementController movementController = new CharacterMovementController(gameClient.getGame().getPlayers().get(0), gameClient);
        gameWindow.registerMovementListener(movementController);
    }

    public void updateGameWindow() {
        gameWindow.update();
    }

    @Override
    public void initializeGameLogic() {

    }

    @Override
    public void showWinningScreen() {

    }

    @Override
    public void deconnectFromNetwork() {

    }

    public void startGameAsHost() {

    }

    public void startGameAsClient() {

    }

    public static void main(String[] args){
        System.out.println("Please call the other methods here when implemented");
        GameClient gameClient = new GameClient("127.0.0.1", 12345);
        GameController gameController = new GameController(gameClient);
        gameController.initializeGameLogic();
        gameController.initializeGameUI();
        //gameClient.run();
    }
}
