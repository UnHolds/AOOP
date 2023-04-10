package game.ui;
import game.core.models.Game;

import javax.swing.*;

public class GameWindow {

    private JFrame window;
    private StartScreenPanel startScreenPanel;
    private GameFieldPanel gameFieldPanel;
    private WinScreenPanel winScreenPanel;

    private Game game;

    public GameWindow(Game game){
        this.window = new JFrame("Cat & Mouse Game");
        this.game = game;
    }

    public void initWindow() {
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setResizable(false);

        this.startScreenPanel = new StartScreenPanel();
        //this.window.add(this.startScreenPanel);
        this.window.add(new GameFieldPanel(game));

        this.window.pack(); // fit the window size around the components
        this.window.setLocationRelativeTo(null);
        this.window.setVisible(true);
    }

    public void showGameFieldPanel() {
        this.gameFieldPanel = new GameFieldPanel(game);
        this.window.remove(this.startScreenPanel);
        this.window.add(gameFieldPanel);
        this.window.addKeyListener(gameFieldPanel);
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
}
