package game.ui;
import game.core.models.IGame;

import javax.swing.*;
import java.awt.event.KeyListener;

public class GameWindow {

    private JFrame window;
    private StartScreenPanel startScreenPanel;
    private GameFieldPanel gameFieldPanel;
    private WinScreenPanel winScreenPanel;

    private IGame game;

    public GameWindow(IGame game){
        this.window = new JFrame("Cat & Mouse Game");
        gameFieldPanel = new GameFieldPanel(game);
        startScreenPanel = new StartScreenPanel();
        this.game = game;
    }

    public void initWindow() {
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);
        this.window.add(this.startScreenPanel);

        this.window.pack(); // fit the window size around the components
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);
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

    public void registerMovementListener(KeyListener listener) {
        window.addKeyListener(listener);
    }
}
