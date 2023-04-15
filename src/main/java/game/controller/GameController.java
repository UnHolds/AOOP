package game.controller;


import game.core.GameServer;
import game.core.handler.CharacterMovementController;
import game.ui.GameWindow;

public class GameController implements IGameController {
    private GameServer gameServer;
    private GameWindow gameWindow;

    public GameController(GameServer gameServer) {
        gameServer.setGameController(this);
        this.gameServer = gameServer;
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
        gameWindow = new GameWindow(gameServer.getGame());
        gameWindow.initWindow();
        CharacterMovementController movementController = new CharacterMovementController(gameServer.getGame().getPlayers().get(0));
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

    public static void main(String[] args){
        System.out.println("Please call the other methods here when implemented");
        GameServer gameServer = new GameServer();
        GameController gameController = new GameController(gameServer);
        gameController.initializeGameLogic();
        gameController.initializeGameUI();
        gameServer.run();
    }
}
