package game.ui;
import game.core.handler.CharacterMovementController;
import game.core.models.IGame;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameWindow {

    private JFrame window;
    private StartScreenPanel startScreenPanel;
    private GameFieldPanel gameFieldPanel;
    private WinScreenPanel winScreenPanel;

    private BlockingQueue<IUiGameConfig> gameConfigs = new LinkedBlockingQueue<>();

    private IGame game;

    public GameWindow(){
        this.window = new JFrame("Cat & Mouse Game");
        this.startScreenPanel = new StartScreenPanel(gameConfigs);
    }

    public IUiGameConfig getGameConfig(){
        try {
            return this.gameConfigs.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    public void initWindow() {
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);
        this.window.add(this.startScreenPanel);

        this.window.pack(); // fit the window size around the components
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);
    }

    public void setGame(IGame game){
        this.game = game;
        this.gameFieldPanel = new GameFieldPanel(this.game);
    }

    public void showGameFieldPanel() {
        this.window.remove(this.startScreenPanel);
        this.window.add(gameFieldPanel);
        this.window.pack();
        this.window.setVisible(true);
    }

    public void showWinScreenPanel() {
        this.winScreenPanel = new WinScreenPanel(game);
        this.window.remove(this.gameFieldPanel);
        this.window.add(winScreenPanel);
        this.window.pack();
        this.window.setVisible(true);
    }

    public void update() {
        window.repaint();
    }

    public void registerMovementListener(CharacterMovementController listener) {
        listener.registerKeyBinding(this.gameFieldPanel);

    }
}
